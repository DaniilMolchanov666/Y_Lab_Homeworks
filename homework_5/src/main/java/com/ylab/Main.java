package com.ylab;

import com.ylab.annotation.EnableAudit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Метод для запуска приложения с внедрением возможности аудирования
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAudit
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
