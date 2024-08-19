package com.ylab.exception;

public class NotAuthorizedException extends Exception{
    @Override
    public String getMessage() {
        return "Данный пользователь не зарегистрирован!";
    }
}
