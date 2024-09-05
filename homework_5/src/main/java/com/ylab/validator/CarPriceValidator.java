package com.ylab.validator;

import com.ylab.annotation.CarShopPriceValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Класс-валидатор для проверки валидности цены автомобиля
 */
public class CarPriceValidator implements ConstraintValidator<CarShopPriceValid, String> {

    /**
     * Инициализация аннотации @CarShopPriceValid
     * @param constraintAnnotation - аннотация CarShopPriceValid
     */
    @Override
    public void initialize(CarShopPriceValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Проверка на валидность стоимости автомобиля (целое число)
     * @param price - поле price
     * @param constraintValidatorContext - объект ConstraintValidatorContext
     * @return результат валидации в виде boolean
     */
    @Override
    public boolean isValid(String price, ConstraintValidatorContext constraintValidatorContext) {
        return price.matches("^\\d+$");
    }
}
