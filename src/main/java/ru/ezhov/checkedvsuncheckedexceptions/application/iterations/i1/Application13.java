package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import java.io.IOException;

/**
 * Попробуем запустить наше приложение в разных сценариях.
 */
public class Application13 {
    public static void main(String[] args) throws IOException { // проброс ошибки до пользователя
        // отсутствующий файл.
        // Видим, что сообщение не изменилось и в лог ничего не записалось, такое поведение нам и нужно
//        Application12.main(new String[]{"1.csv"});

        // отсутствует возраст 'Error when get employees. Please contact the developers. Age must be a number. Incorrect value '''.
        // Для других исключений мы выводим понятный пользователю текст
        Application12.main(new String[]{"employees-bad-empty-age.csv"});
    }

    /**
     * Что мы имеем на данный момент:
     * 1. Пользователи довольны, они понимают, что происходит.
     * 2. Логи пишутся и в случае необходимости мы можем их запросить
     * 3. Мы полностью контролируем наши исключения и обрабатываем все контракты методов с одни лишь нюансом -
     * для понимания контрактов методов нам необходимо постоянно смотреть в код
     *
     * Давайте перепишем наш код с проверяемыми исключениями и сравним разницу
     *
     * @see Application14
     */
}

