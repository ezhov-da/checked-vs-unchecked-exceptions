package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

/**
 * На что стоит обратить внимание.
 * <p>
 * Клиент, в нашем случае метод 'example2' способен предвидеть ошибку и корректно её обработать.
 * <p>
 * А как мы видели ранее корректность обработки - это достаточно важный показатель удобства
 * работы с приложением и его отладки.
 *
 * Давайте вернёмся к нашему приложению:
 * @see ru.ezhov.checkedvsuncheckedexceptions.application.iterations.it2.Application23
 */
public class Application22 {
    public static void main(String[] args) {
        example2(new PlainInformationEx());
        example2(new JsonInformationEx());
    }

    private static void example2(InformationEx information) {
        try {
            System.out.println(information.information());
        } catch (InformationException e) {
            System.out.println("Unexpected error. Please try again");
        }
    }
}

interface InformationEx {
    String information() throws InformationException;
}

class JsonInformationEx implements InformationEx {

    @Override
    public String information() throws InformationException {
        throw new InformationException("Error");
    }
}

class PlainInformationEx implements InformationEx {

    @Override
    public String information() throws InformationException {
        return "Hello";
    }
}

class InformationException extends Exception {
    public InformationException(String message) {
        super(message);
    }
}