package ru.fors.pages;

public class LoginException extends Exception {
    public static final String DEFAULT_MESSAGE = "Вход не выполнен. Ошибка.";

    public LoginException() {
        super(DEFAULT_MESSAGE);
    }
}