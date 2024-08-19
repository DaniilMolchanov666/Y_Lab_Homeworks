package com.ylab.exception;

public class AlreadyRegistrationUserException extends Exception{
    @Override
    public String getMessage() {
        return "Пользователь с таким именем уже зарегистрирован!";
    }
}
