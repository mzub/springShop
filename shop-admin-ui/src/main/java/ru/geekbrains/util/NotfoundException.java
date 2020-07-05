package ru.geekbrains.util;

import liquibase.pro.packaged.X;

import java.util.function.Supplier;

public class NotfoundException extends RuntimeException implements Supplier<X> {
    @Override
    public X get() {
        return null;
    }
}
