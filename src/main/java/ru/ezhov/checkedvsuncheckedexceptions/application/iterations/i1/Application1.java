package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Поступила задача на создание приложения, которое читает заданный файл формата CSV.
 * И выводит информацию из него в консоль в формате JSON.
 * <p>
 * Решаем задачу в лоб.
 *
 * На что стоит обратить внимание и какие "скрытые" проблемы присутствуют в коде?
 */
public class Application1 {
    public static void main(String[] args) throws IOException {
        File file = new File("employees.csv");
        List<Employee> employees = getEmployees(file);
        String employeesAsJson = toJson(employees);
        System.out.println(employeesAsJson);
    }

    private static List<Employee> getEmployees(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        return lines.stream()
                .map(l -> {
                            String[] values = l.split(";");
                            return new Employee(values[0], Integer.valueOf(values[1]));
                        }
                )
                .toList();
    }

    private static String toJson(List<Employee> employees) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        String employeesAsString = employees
                .stream()
                .map(e -> String.format("{\"name\":\"%s\",\"age\":\"%s\"}", e.name, e.age))
                .collect(Collectors.joining(","));
        builder.append(employeesAsString);
        builder.append("]");
        return builder.toString();
    }

    private static class Employee {
        private String name;
        private int age;

        public Employee(String name, int age) {
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

