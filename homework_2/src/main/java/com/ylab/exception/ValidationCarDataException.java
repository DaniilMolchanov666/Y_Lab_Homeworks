package com.ylab.exception;

public class ValidationCarDataException extends Exception{

    @Override
    public String getMessage() {
        return "\nГод и цена должны быть целыми числами! Введите еще раз!";
    }
}
