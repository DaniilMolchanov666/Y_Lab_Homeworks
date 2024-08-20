package com.ylab.servlet;

import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public interface CarShopServlet {

    default String getJson(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return "Не удалось прочитать!";
        }
    }

    default void createResponse(int status, String body, HttpServletResponse response) throws IOException {
        response.setStatus(status);
        response.getWriter().println(body);
    }
}
