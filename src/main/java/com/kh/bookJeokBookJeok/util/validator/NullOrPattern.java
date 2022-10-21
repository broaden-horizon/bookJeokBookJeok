package com.kh.bookJeokBookJeok.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NullOrPatternValidator.class})
public @interface NullOrPattern {
    String message() default "공백이 아니어야 합니다";
    String pattern() default ".";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
