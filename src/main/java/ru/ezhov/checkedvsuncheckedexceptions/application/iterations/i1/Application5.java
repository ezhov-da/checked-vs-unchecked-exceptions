package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import java.io.IOException;

/**
 * Попробуем запустить наше приложение в разных сценариях.
 */
public class Application5 {
    public static void main(String[] args) throws IOException { // проброс ошибки до пользователя
//        Application4.main(new String[]{"1.csv"}); // отсутствующий файл FileNotFoundException. Стало немного понятнее, но стектрейс пугает
//        Application4.main(new String[]{"employees-bad-delimeter.csv"}); // ошибка из-за некорректных разделителей пропала
//        Application4.main(new String[]{"employees-bad-empty-age.csv"}); // отсутствует возраст. ошибка заменилась на IllegalArgumentException, но так же пугает стектрейсом
//        Application4.main(new String[]{"employees-bad-quotes.csv"}); // ошибка пропала, мы молодцы
    }

    /**
     * Что мы имеем на данный момент:
     * 1. У нас остались разные исключения и что самое интересное, эти исключения зависят от реализации и добавляемой проверки
     * 2. Исключения, которые мы прокидываем как проверяемые, так и непроверяемые
     * 3. При формировании JSON появилось новое исключение JsonProcessingException, о котором ранее ни кто не знал
     * 4. Но в целом мы молодцы - можем гордиться своей работой!
     *
     * @see Application6
     */
}

