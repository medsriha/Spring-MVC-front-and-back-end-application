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

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MatchingPasswordValidator.class)
@Documented
public @interface ValidMatchingPassword {
    String message() default "Two passwords are not identical!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
