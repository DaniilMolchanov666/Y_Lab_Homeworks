package com.ylab.exception;

public class NoAuthenticatedException extends Exception{
    @Override
    public String getMessage() {
        return "Данный пользователь не зарегистрирован!";
    }
}
