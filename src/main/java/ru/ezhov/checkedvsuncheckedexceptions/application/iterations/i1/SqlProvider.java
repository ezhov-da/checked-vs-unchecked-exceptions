package ru.ezhov.checkedvsuncheckedexceptions.application.iterations.i1;

public class SqlProvider {
    public static String url(String dbFile ) {
        return "jdbc:sqlite:./" + dbFile + ".s3db";
    }
}
