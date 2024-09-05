package com.ylab.validator;

import com.ylab.annotation.CarShopYearValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;
import java.util.Arrays;

/**
 * Класс-валидатор для проверки валидности года выпуска автомобиля
 */
public class CarValidator implements ConstraintValidator<CarShopYearValid, String> {

    /**
     * Инициализация аннотации @CarShopYearValid
     * @param constraintAnnotation - аннотация CarShopYearValid
     */
    @Override
    public void initialize(CarShopYearValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Проверка на валидность года выпуска автомобиля
     * - целое число из 4 цифр
     * - не раньше 1970 года и не позже нынешнего
     * @param year - поле year
     * @param constraintValidatorContext - объект ConstraintValidatorContext
     * @return результат валидации в виде boolean
     */
    @Override
    public boolean isValid(String year, ConstraintValidatorContext constraintValidatorContext) {
        int currentYear = Year.now().getValue();
        int minSizeOfYearValue = 4;
        int minYear = 1970;

        return Arrays.stream(year.split("")).allMatch(i -> Character.isDigit(i.charAt(0)))
                && year.split("").length == minSizeOfYearValue
                && ((Integer.parseInt(year) >= minYear
                && Integer.parseInt(year) <= currentYear));
    }
}
