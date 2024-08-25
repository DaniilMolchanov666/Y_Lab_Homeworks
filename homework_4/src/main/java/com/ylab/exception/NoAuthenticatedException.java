package com.ylab.exception;

/**
 * Исключение для ситуации попытки авторизации незарегистрированного пользователя
 */
public class NoAuthenticatedException extends Exception{
    @Override
    public String getMessage() {
        return "Данный пользователь не зарегистрирован!";
    }
}
