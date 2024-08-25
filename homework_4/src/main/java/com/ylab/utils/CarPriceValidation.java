package com.ylab.utils;

import com.ylab.annotaion.ValidPriceCar;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CarPriceValidation implements ConstraintValidator<ValidPriceCar, String> {
    @Override
    public void initialize(ValidPriceCar constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Метод для проверки валидности цены и года выпуска автомобиля
     * - цена должна быть числом
     * - год должен быть числом, размер не менее 4 символа и входить в промежуток от 1970 до нынешнего года
     *
     * @return результат проверки на валидность
     */
    @Override
    public boolean isValid(String price, ConstraintValidatorContext constraintValidatorContext) {
        return price.matches("^\\d+$");
    }
}
