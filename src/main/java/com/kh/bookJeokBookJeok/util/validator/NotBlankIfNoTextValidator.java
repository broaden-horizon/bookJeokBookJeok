package com.kh.bookJeokBookJeok.util.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankIfNoTextValidator implements ConstraintValidator<NotBlankIfNoText, String> {

    @Override
    public void initialize(NotBlankIfNoText constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || StringUtils.hasText(value);
    }
}
