package ru.ezhov.checkedvsuncheckedexceptions.examples;

/**
 * Данный пример демонстрирует непроверяемое исключение и отсутствие необходимости его обработки.
 *
 * 1 - обратите внимание, что пример описан в методе main
 */
public class UncheckedExceptionExample1 {
    public static void main(String[] args) {
        divideByZero(); // 1
    }

    private static void divideByZero() {
        int numerator = 1;
        int denominator = 0;
        int result = numerator / denominator;
    }
}
