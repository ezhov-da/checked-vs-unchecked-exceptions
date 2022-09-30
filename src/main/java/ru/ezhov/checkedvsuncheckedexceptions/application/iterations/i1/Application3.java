package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Попробуем запустить наше приложение в разных сценариях.
 */
public class Application3 {
    public static void main(String[] args) throws IOException { // проброс ошибки до пользователя
//        Application2.main(new String[]{"1.csv"}); // отсутствующий файл FileNotFoundException. По стектрейсу (кто его умеет читать) можно понять, что проблема в отсутствующем файле
//        Application2.main(new String[]{"employees-bad-delimeter.csv"}); // некорректные разделители у файла. NumberFormatException
//        Application2.main(new String[]{"employees-bad-empty-age.csv"}); // отсутствует возраст. ArrayIndexOutOfBoundsException
//        Application2.main(new String[]{"employees-bad-quotes.csv"}); // в имени двойные кавычки. Ошибки никакой нет, но JSON строится некорректный
    }

    /**
     * Что мы имеем на данный момент:
     * 1. Ошибка об отсутствии файла нечитаема для пользователя и предполагает обращение к разработке по "декодированию" сообщения
     * 2. Ошибка о некорректном формате вообще непонятна для пользователя и предполагает точное обращение к разработке
     * 3. Ошибка о выходе за пределы диапазона так же непонятна для пользователя и предполагает точное обращение к разработке
     * 4. Формирование некорректного JSON нарушает постановку задачи
     *
     * Что следует сделать?
     *
     * @see Application4
     */
}

