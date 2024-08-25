package com.ylab.exception;

/**
 * Исключение для ситуации попытки отправить невалидные данные автомобиля
 */
public class ValidationCarDataException extends Exception{

    @Override
    public String getMessage() {
        return "\nГод и цена должны быть целыми числами! Введите еще раз!";
    }
}
