package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * На что стоит обратить внимание и какие "скрытые" проблемы присутствуют в коде?
 *
 * @see Application3
 */
public class Application2 {
    public static void main(String[] args) throws IOException { // проброс ошибки до пользователя
        String fileName;
        if (args.length == 1) {
            fileName = args[0];
        } else {
            fileName = "employees.csv";
        }
        File file = new File(fileName); //отсутствие проверки наличия файла

        List<Employee> employees = getEmployees(file);
        String employeesAsJson = toJson(employees);

        System.out.println(employeesAsJson);
    }

    private static List<Employee> getEmployees(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        return lines.stream()
                .map(l -> {
                            String[] values = l.split(","); // чтение файла CSV без учёта экранирования разделителей
                            return new Employee(values[0], Integer.valueOf(values[1])); // 2 отсутствует проверка на наличие данных
                        }
                )
                .toList();
    }

    private static String toJson(List<Employee> employees) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        String employeesAsString = employees
                .stream()
                .map(e -> String.format("{\"name\":\"%s\",\"age\":\"%s\"}", e.getName(), e.getAge())) // формирование JSON без учёта экранирования данных
                .collect(Collectors.joining(","));
        builder.append(employeesAsString);
        builder.append("]");
        return builder.toString();
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

