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
 * @see Application19
 */

public class Application18 {
    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        String path = Application18.class
                .getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);

    }

    private static final Logger logger = Logger.getLogger(Application10.class.getName());

    public static void main(String[] args) {
        try {
            List<Employee> employees = getEmployees();
            String employeesAsJson = toJson(employees);
            System.out.println(employeesAsJson);
        } catch (GetEmployeesApplication18Exception ex) {
            logger.log(Level.SEVERE, "Error when get employees", ex);
            System.out.println("Error when get employees. Please contact the developers. " + ex.getMessage()); // на данном этапе важно понимать, что сообщение исключения может содержать данные недопустимые для отображения пользователю
        } catch (ConvertEmployeesApplication18Exception ex) {
            logger.log(Level.SEVERE, "Error when convert employees to json", ex);
            System.out.println("Error when convert employees to json. Please contact the developers. " + ex.getMessage()); // на данном этапе важно понимать, что сообщение исключения может содержать данные недопустимые для отображения пользователю
        }
    }

    private static List<Employee> getEmployees() throws GetEmployeesApplication18Exception {
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
                    throw new GetEmployeesApplication18Exception(
                            "Age of employee '" + name + "' should not be less than '0' but now '" + age + "'"
                    );
                }
                employees.add(new Employee(name, age));
            }

            return employees;
        } catch (SQLException e) {
            throw new GetEmployeesApplication18Exception("Error when get employees from db", e);
        }
    }

    private static String toJson(List<Employee> employees) throws ConvertEmployeesApplication18Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            throw new ConvertEmployeesApplication18Exception("Error when convert employees", e);
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

class GetEmployeesApplication18Exception extends Exception {

    public GetEmployeesApplication18Exception(String message) {
        super(message);
    }

    public GetEmployeesApplication18Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

class ConvertEmployeesApplication18Exception extends Exception {
    public ConvertEmployeesApplication18Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

