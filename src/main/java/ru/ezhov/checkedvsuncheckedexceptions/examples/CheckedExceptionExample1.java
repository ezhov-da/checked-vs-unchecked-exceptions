package ru.ezhov.checkedvsuncheckedexceptions.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Данный пример демонстрирует проверяемое исключение и необходимость его обработки.
 * <p>
 * 1 - обратите внимание, что пример описан в методе main
 */
public class CheckedExceptionExample1 {
    public static void main(String[] args) {
        try {
            checkedExceptionWithThrows(); // 1
        } catch (FileNotFoundException e) {
            // необходимо обработать исключение или оставить так (bad practice)
        }
    }

    private static void checkedExceptionWithThrows() throws FileNotFoundException {
        File file = new File("not_existing_file.txt");
        FileInputStream stream = new FileInputStream(file);
    }
}
