package ru.rostislav.exception;

public class NullUserException extends RuntimeException {
    public NullUserException(String message) {
        super(message);
    }
}
