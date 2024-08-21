package com.ylab.exception;

/**
 * Исключение для ситуации отсутствия прав доступа
 */
public class NotAccessOperationException extends Exception{

    @Override
    public String getMessage() {
        return "У вас недостаточно прав!";
    }
}
