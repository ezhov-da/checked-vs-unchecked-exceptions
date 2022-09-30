package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import java.io.IOException;

/**
 * Попробуем запустить наше приложение в разных сценариях.
 */
public class Application19 {
    public static void main(String[] args) throws IOException { // проброс ошибки до пользователя
        // выполняем с некорректной базой данных
        // получаем сообщение 'Error when get employees. Please contact the developers. Error when get employees from db'
//        setUrlToDb("employees!!!!!!!!!!!!!");
//        Application18.main(new String[]{});

        // выполняем с корректными данными
//        setUrlToDb("employees");
//        Application18.main(new String[]{});

        // возраст -1 'Error when get employees. Please contact the developers. Age of employee 'Eric Evans' should not be less than 0 but now '-1''.
        // Для других исключений мы выводим понятный пользователю текст
//        setUrlToDb("employees-bad-empty-age");
//        Application18.main(new String[]{});
    }

    private static void setUrlToDb(String dbFileName) {
        System.setProperty("database.connection.url", SqlProvider.url(dbFileName));

    }

    /**
     * Что мы имеем на данный момент:
     * 1. Работа с файлом прекращена
     * 2. Успешно интегрировались с БД
     * 3. Убрали исключение, которое больше ненужно (нам помог компилятор)
     *
     * Подведём промежуточный итог
     * @see Application20
     */
}

