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
 * 1. Пользователи довольны, они понимают, что происходит.
 * 2. Логи пишутся и в случае необходимости мы можем их запросить
 * 3. Мы полностью контролируем наши исключения и обрабатываем все контракты методов с одни лишь нюансом - для понимания контрактов методов нам необходимо постоянно смотреть в код
 * <p>
 * Мы молодцы - мы сделали хорошую работу!
 * <p>
 * Время идёт, пользователи работают. Ошибки копятся.
 * В один из дней приходит бизнес и говорит - мы хотим понимать, когда нам нужно обращаться к разработчикам, а когда нет,
 * например, если мы указываем некорректный файл - то мы можем это исправить, а если что-то в данных, то нет и тогда нам нужна подсказка.
 * <p>
 * Мы, как разработчик, подумали и приняли решение, а ведь действительно, отсутствие файла - это ошибка, которая по сути не нужна в логе, с ней всё понятно.
 * И хорошо бы при её возбуждении не логировать.
 * <p>
 * Как поступим?
 * <p>
 * 1. Сделаем непроверяемые исключения проверяемыми
 * 2. Обработаем его в методе main
 *
 * @see Application15
 */

public class Application14 {
    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        String path = Application14.class
                .getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);

    }

    private static final Logger logger = Logger.getLogger(Application10.class.getName());

    public static void main(String[] args) {
        String fileName;
        if (args.length == 1) {
            fileName = args[0];
        } else {
            fileName = "employees.csv";
        }
        File file = new File(fileName);

        try {
            List<Employee> employees = getEmployees(file);
            String employeesAsJson = toJson(employees);
            System.out.println(employeesAsJson);
        } catch (GetEmployeesApplication14Exception ex) {
            logger.log(Level.SEVERE, "Error when get employees", ex);
            System.out.println("Error when get employees. Please contact the developers. " + ex.getMessage()); // на данном этапе важно понимать, что сообщение исключения может содержать данные недопустимые для отображения пользователю
        } catch (ConvertEmployeesApplication14Exception ex) {
            logger.log(Level.SEVERE, "Error when convert employees to json", ex);
            System.out.println("Error when convert employees to json. Please contact the developers. " + ex.getMessage()); // на данном этапе важно понимать, что сообщение исключения может содержать данные недопустимые для отображения пользователю
        } catch (FileNotFoundEmployeesApplication14Exception ex) {
            System.out.println("File not found. " + ex.getMessage());
        }
    }

    private static List<Employee> getEmployees(File file) throws GetEmployeesApplication14Exception, FileNotFoundEmployeesApplication14Exception {
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
                                     * Обратите внимание, ввиду особенностей работы lambda выражений, нам пришлось
                                     * выкидывать непроверяемые исключения
                                     */
                                    throw new IllegalArgumentException("Age must be a number. Incorrect value '" + row[1] + "'");
                                }
                                return new Employee(row[0], age);
                            } else {
                                throw new IllegalArgumentException("Incorrect data in row");
                            }
                        })
                        .toList();
            } catch (CsvException | IOException e) {
                throw new GetEmployeesApplication14Exception("Error when read file '" + file.getName() + "'", e);
            } catch (IllegalArgumentException ex) {
                /**
                 * А здесь преобразовывать его уже в наше
                 */
                throw new GetEmployeesApplication14Exception(ex.getMessage(), ex);
            }
        } else {
            throw new FileNotFoundEmployeesApplication14Exception("File '" + file.getName() + "' not found.");
        }
    }

    private static String toJson(List<Employee> employees) throws ConvertEmployeesApplication14Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            throw new ConvertEmployeesApplication14Exception("Error when convert employees", e);
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

class GetEmployeesApplication14Exception extends Exception {

    public GetEmployeesApplication14Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

class FileNotFoundEmployeesApplication14Exception extends Exception {

    public FileNotFoundEmployeesApplication14Exception(String message) {
        super(message);
    }

}

class ConvertEmployeesApplication14Exception extends Exception {
    public ConvertEmployeesApplication14Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

