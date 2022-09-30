package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Прошло какое-то время, ошибки время от времени появлялись и бизнес пришёл с просьбой,
 * чтоб скрыть огромные "полотна" ошибок из вида.
 * Оставив только понятные человеку сообщения.
 *
 * Давайте попробуем это сделать.
 *
 * 1. Сначала нужно избавиться от исключения в методе main, которое мы пробрасываем
 * 2. Отловить исключения из наших методов
 * 3. Попробуем описать причины возникновения понятными сообщениями
 *
 * @see Application7
 */
public class Application6 {
    public static void main(String[] args) throws IOException { // проброс ошибки до пользователя
        String fileName;
        if (args.length == 1) {
            fileName = args[0];
        } else {
            fileName = "employees.csv";
        }
        File file = new File(fileName);

        List<Employee> employees = getEmployees(file);
        String employeesAsJson = toJson(employees);

        System.out.println(employeesAsJson);
    }

    private static List<Employee> getEmployees(File file) throws IOException {
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
                                    throw new IllegalArgumentException("Age must be a number. Incorrect value '" + row[1] + "'"); // так же добавлена проверка на возраст
                                }
                                return new Employee(row[0], age);
                            } else {
                                throw new IllegalArgumentException("Incorrect data in row"); // выглядит так, что данный тип исключения лучше подходит чем IOException
                            }
                        })
                        .toList();
            } catch (CsvException e) {
                // обратите внимание, данная библиотека добавляет своё проверяемое исключение, для того,
                // чтоб не менять сигнатуру метода, мы обернём это исключение в "наше" и выбросим дальше
                throw new IOException("Error when read file '" + file.getName() + "'", e);
            }
        } else {
            throw new FileNotFoundException("File '" + file.getName() + "' not found."); // учитывая то, что наш метод возбуждает IOException, можем воспользоваться его потомком
        }
    }

    private static String toJson(List<Employee> employees) throws JsonProcessingException { // так как парсер кидает исключение добавим его в сигнатуру метода
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(employees);
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

