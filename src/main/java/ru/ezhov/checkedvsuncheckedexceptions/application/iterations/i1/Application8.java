package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

import java.io.IOException;

/**
 * Попробуем запустить наше приложение в разных сценариях.
 */
public class Application8 {
    public static void main(String[] args) throws IOException { // проброс ошибки до пользователя
        // отсутствующий файл 'Error when get employees from source or create json'. Видим, что теперь для пользователя нет страшного стектрейса,
        // но понятности не сильно прибавилось, например, непонятно, может ли пользователь сам решить проблему или проблема в некорректных данных?
//        Application7.main(new String[]{"1.csv"});

        // отсутствует возраст 'Unexpected error'.
        // Теперь мы не пугаем пользователя стектрейсом, но и не добавляем понятности происходящему
        Application7.main(new String[]{"employees-bad-empty-age.csv"});
    }

    /**
     * Что мы имеем на данный момент:
     * 1. Пользователи довольны, но работы нам прибавилось, так как теперь по каждой проблеме пользователи начали ходить к нам
     * 2. Нам стало более сложно воспроизводить ошибку и отлаживать код, так как отсутствуют стектрейсы
     *
     * Как бы нам решить эту проблему?
     *
     * @see Application9
     */
}

