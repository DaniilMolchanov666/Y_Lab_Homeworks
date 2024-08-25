package com.ylab.exception;

/**
 * Исключение при попытке сделать запрос в случае отсутствия авторизации
 */
public class NotAuthException extends Exception {
    @Override
    public String getMessage() {
        return "Вы не авторизованы! \nПожалуйста, войдите под своим логином и паролем!";
    }
}
