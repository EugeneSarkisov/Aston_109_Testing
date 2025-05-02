package ru.aston.crud_impl.util.user_exceptions;

public class IncorrectInputException extends Exception {
    public IncorrectInputException() {
    }

    public IncorrectInputException(String message) {
        super(message);
    }
}
