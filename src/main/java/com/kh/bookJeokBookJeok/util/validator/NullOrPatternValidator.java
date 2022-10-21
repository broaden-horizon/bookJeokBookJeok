package com.kh.bookJeokBookJeok.util.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NullOrPatternValidator implements ConstraintValidator<NullOrPattern, String> {
    private String pattern;
    @Override
    public void initialize(NullOrPattern constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return Pattern.matches(pattern, value);
    }


}
