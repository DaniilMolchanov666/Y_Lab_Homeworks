package com.ylab.exception;

/**
 * Исключение для ситуации повторной регистрации пользователем
 */
public class AlreadyRegistrationUserException extends Exception{
    @Override
    public String getMessage() {
        return "Пользователь с таким именем уже зарегистрирован!";
    }
}
