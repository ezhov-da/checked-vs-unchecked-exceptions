package ru.ezhov.checkedvsuncheckedexceptions.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Данный пример демонстрирует проверяемое исключение и прокидывание его дальше.
 *
 * 1 - обратите внимание, что пример описан в методе main
 */
public class CheckedExceptionExample2 {
    public static void main(String[] args) throws FileNotFoundException {
        checkedExceptionWithThrows(); // 1
    }

    private static void checkedExceptionWithThrows() throws FileNotFoundException {
        File file = new File("not_existing_file.txt");
        FileInputStream stream = new FileInputStream(file);
    }
}
