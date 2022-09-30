package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Что мы имеем на данный момент:
 * 1. Пользователи довольны, но работы нам прибавилось, так как теперь по каждой проблеме пользователи начали ходить к нам
 * 2. Нам стало более сложно воспроизводить ошибку и отлаживать код, так как отсутствуют стектрейсы
 * <p>
 * Что будем делать?
 * <p>
 * 1. Наверно, нам стоит всё же разделить понимание того, что именно не так. Согласны?
 * Варианта по реализации 2:
 * 1. Использовать непроверяемые исключения для каждого случая
 * 2. Использовать проверяемые исключения для каждого случая
 * <p>
 * Давайте реализуем сначала непроверяемые исключения.
 * 1. Одно исключение для получения сотрудников
 *
 * @see GetEmployeesApplication10Exception
 * 2. Одно исключение для преобразования в Json
 * @see ConvertEmployeesApplication10Exception
 * <p>
 * 2. Дорогие нам стектрейсы не хотелось бы терять, по-этому будем их логировать в файл, чтоб просить его у пользователей
 */

public class Application10 {
    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        String path = Application10.class
                .getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);

    }

    /**
     * добавляем логгер
     */
    private static final Logger logger = Logger.getLogger(Application10.class.getName());

    public static void main(String[] args) { // проброс ошибки до пользователя
        String fileName;
        if (args.length == 1) {
            fileName = args[0];
        } else {
            fileName = "employees.csv";
        }
        File file = new File(fileName);

        /**
         * Что меняется здесь.
         * Теперь у нас есть два понятных нам исключения.
         * @see GetEmployeesApplication10Exception
         * @see ConvertEmployeesApplication10Exception
         *
         * Мы можем их обработать.
         */
        try {
            List<Employee> employees = getEmployees(file);
            String employeesAsJson = toJson(employees);
            System.out.println(employeesAsJson);
        } catch (GetEmployeesApplication10Exception ex) {
            logger.log(Level.SEVERE, "Error when get employees", ex);
            System.out.println("Error when get employees. " + ex.getMessage()); // на данном этапе важно понимать, что сообщение исключения может содержать данные недопустимые для отображения пользователю
        } catch (ConvertEmployeesApplication10Exception ex) {
            logger.log(Level.SEVERE, "Error when convert employees to json", ex);
            System.out.println("Error when convert employees to json. " + ex.getMessage()); // на данном этапе важно понимать, что сообщение исключения может содержать данные недопустимые для отображения пользователю
        }
    }

    private static List<Employee> getEmployees(File file) {
        if (file.exists()) {
            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                List<String[]> rows = reader.readAll();
                return rows
                        .stream()
                        .map(row -> {
                            if (row.length == 2) {
                                int age;
                                try {
                                    age = Integer.valueOf(row[1]);
                                } catch (NumberFormatException ex) {
                                    /**
                                     * Так как теперь у нас есть одно исключение для этого сценария,
                                     * мы можем заменить на исключение все остальные исключения
                                     */
                                    throw new GetEmployeesApplication10Exception("Age must be a number. Incorrect value '" + row[1] + "'");
                                }
                                return new Employee(row[0], age);
                            } else {
                                throw new GetEmployeesApplication10Exception("Incorrect data in row");
                            }
                        })
                        .toList();
            } catch (CsvException | IOException e) {
                // обратите внимание, данная библиотека добавляет своё проверяемое исключение, для того,
                // чтоб не менять сигнатуру метода, мы обернём это исключение в "наше" и выбросим дальше
                throw new GetEmployeesApplication10Exception("Error when read file '" + file.getName() + "'", e);
            }
        } else {
            throw new GetEmployeesApplication10Exception("File '" + file.getName() + "' not found.");
        }
    }

    private static String toJson(List<Employee> employees) { // так как парсер кидает исключение добавим его в сигнатуру метода
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            throw new ConvertEmployeesApplication10Exception("Error when convert employees", e);
        }
    }

    private static class Employee {
        private String name;
        private int age;

        public Employee(String name, int age) { // нет понимания валидации данных. Возраст меньше 0? Имя пустое?
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}

class GetEmployeesApplication10Exception extends RuntimeException {

    public GetEmployeesApplication10Exception(String message) {
        super(message);
    }

    public GetEmployeesApplication10Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

class ConvertEmployeesApplication10Exception extends RuntimeException {
    public ConvertEmployeesApplication10Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

