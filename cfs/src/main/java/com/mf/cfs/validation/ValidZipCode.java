package com.mf.cfs.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ZipcodeValidator.class)
@Documented
public @interface ValidZipCode {
    String message() default "Invalid Zipcode Format: must be a five-digit number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
