package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Бизнес пришёл и сказал, что теперь, нужно брать информацию из базы данных, а не из файла.
 * И для начала, мы откорректируем код, в котором использовали непроверяемые исключения.
 * <p>
 * Что будем делать?
 * 1. Подключимся к базе данных
 * 2. Получим данные
 * <p>
 * Кто-то видит какие-то проблемы?
 * <p>
 * Давайте выполним код.
 *
 * @see Application17
 */

public class Application16 {
    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        String path = Application16.class
                .getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);

    }

    private static final Logger logger = Logger.getLogger(Application10.class.getName());

    public static void main(String[] args) {
        try {
            List<Employee> employees = getEmployees();
            String employeesAsJson = toJson(employees);
            System.out.println(employeesAsJson);
        } catch (GetEmployeesApplication16Exception ex) {
            logger.log(Level.SEVERE, "Error when get employees", ex);
            System.out.println("Error when get employees. Please contact the developers. " + ex.getMessage());
        } catch (ConvertEmployeesApplication16Exception ex) {
            logger.log(Level.SEVERE, "Error when convert employees to json", ex);
            System.out.println("Error when convert employees to json. Please contact the developers. " + ex.getMessage());
        } catch (FileNotFoundEmployeesApplication16Exception ex) {
            System.out.println("File not found. " + ex.getMessage());
        }
    }

    private static List<Employee> getEmployees() {
        String connectionString = System.getProperty("database.connection.url");
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEES");
             ResultSet result = statement.executeQuery();
        ) {
            List<Employee> employees = new ArrayList<>();
            while (result.next()) {
                String name = result.getString(1);
                int age = result.getInt(2);

                if (age < 0) {
                    throw new GetEmployeesApplication16Exception(
                            "Age of employee '" + name + "' should not be less than '0' but now '" + age + "'"
                    );
                }

                employees.add(new Employee(name, age));
            }

            return employees;
        } catch (SQLException e) {
            throw new GetEmployeesApplication16Exception("Error when get employees from db", e);
        }
    }

    private static String toJson(List<Employee> employees) { // так как парсер кидает исключение добавим его в сигнатуру метода
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            throw new ConvertEmployeesApplication16Exception("Error when convert employees", e);
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

class GetEmployeesApplication16Exception extends RuntimeException {

    public GetEmployeesApplication16Exception(String message) {
        super(message);
    }

    public GetEmployeesApplication16Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

class FileNotFoundEmployeesApplication16Exception extends RuntimeException {

    public FileNotFoundEmployeesApplication16Exception(String message) {
        super(message);
    }

    public FileNotFoundEmployeesApplication16Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

class ConvertEmployeesApplication16Exception extends RuntimeException {
    public ConvertEmployeesApplication16Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

