package com.ylab.annotation;

import com.ylab.validator.CarValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для отметки полей автомобиля, нуждающихся в валидации
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CarValidator.class)
public @interface CarShopYearValid {
    String message() default "Некорректное значение года выпуска автомобиля!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
