package ru.ezhov.checkedvsuncheckedexceptions.examples;

/**
 * Данный пример демонстрирует непроверяемое исключение и отсутствие необходимости его обработки.
 */
public class UncheckedExceptionExample1 {
    public static void main(String[] args) {
        divideByZero();
    }

    private static void divideByZero() {
        int numerator = 1;
        int denominator = 0;
        int result = numerator / denominator;
    }
}
