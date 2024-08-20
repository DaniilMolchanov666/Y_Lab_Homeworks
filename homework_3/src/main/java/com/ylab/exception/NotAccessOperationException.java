package com.ylab.exception;

public class NotAccessOperationException extends Exception{

    @Override
    public String getMessage() {
        return "У вас недостаточно прав!";
    }
}
