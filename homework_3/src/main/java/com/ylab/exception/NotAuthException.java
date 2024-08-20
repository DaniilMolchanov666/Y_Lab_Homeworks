package com.ylab.exception;

public class NotAuthException extends Exception {
    @Override
    public String getMessage() {
        return "Вы не авторизованы! \nПожалуйста, войдите под своим логином и паролем!";
    }
}
