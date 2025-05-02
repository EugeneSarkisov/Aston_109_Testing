package ru.aston.crud_impl.util.user_exceptions;

public class UsernameAlreadyExistException extends Exception {
    public UsernameAlreadyExistException() {
    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
