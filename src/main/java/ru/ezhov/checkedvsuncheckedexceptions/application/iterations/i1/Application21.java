package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

/**
 * Есть такая аббревиатура как SOLID.
 * <p>
 * Думаю все о ней знают.
 * <p>
 * Особый интерес для меня вызывает буква 'L' - https://clck.ru/NKbzU
 * <p>
 * Роберт Мартин привёл его к такому виду:
 * Функции, которые используют базовый тип, должны иметь возможность использовать
 * подтипы базового типа, не зная об этом.
 * <p>
 * На основе данной информации, давайте рассмотрим следующие примеры.
 *
 * Пример интерфейса без исключения
 *
 * @see Application22
 */
public class Application21 {
    public static void main(String[] args) {
        example1(new PlainInformation());
        example1(new JsonInformation());
    }

    private static void example1(Information information) {
        System.out.println(information.information());
    }
}

interface Information {
    String information();
}

class JsonInformation implements Information {

    @Override
    public String information() {
        throw new RuntimeException("Error");
    }
}

class PlainInformation implements Information {

    @Override
    public String information() {
        return "Hello";
    }
}
