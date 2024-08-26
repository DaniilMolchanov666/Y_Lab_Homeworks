package com.ylab.annotaion;

import com.ylab.utils.CarYearValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CarYearValidation.class)
public @interface ValidYearCar {
    String message() default "Invalid car year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
