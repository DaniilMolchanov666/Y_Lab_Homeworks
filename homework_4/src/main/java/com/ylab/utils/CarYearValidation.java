package com.ylab.utils;

import com.ylab.annotaion.ValidYearCar;
import com.ylab.exception.ValidationCarDataException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.time.Year;
import java.util.Arrays;

public class CarYearValidation implements ConstraintValidator<ValidYearCar, String> {

    @Override
    public void initialize(ValidYearCar constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Метод для проверки валидности цены и года выпуска автомобиля
     * - цена должна быть числом
     * - год должен быть числом, размер не менее 4 символа и входить в промежуток от 1970 до нынешнего года
     *
     * @return результат проверки на валидность
     */
    @SneakyThrows
    @Override
    public boolean isValid(String year, ConstraintValidatorContext constraintValidatorContext) {

        int currentYear = Year.now().getValue();
        int minSizeOfYearValue = 4;
        int minYear = 1970;

        boolean isCorrectYear = Arrays.stream(year.split("")).allMatch(i -> Character.isDigit(i.charAt(0)))
                && year.split("").length == minSizeOfYearValue
                && ((Integer.parseInt(year) >= minYear
                && Integer.parseInt(year) <= currentYear));

        if (!isCorrectYear) {
            throw new ValidationCarDataException();
        }
        return true;
    }
}
