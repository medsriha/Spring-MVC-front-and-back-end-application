package com.mf.cfs.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private final static Integer MIN_LENGTH = 6;
    private final static Integer MAX_LENGTH = 20;
    /*
    Pattern Matching criteria:
    1. Search for at least one digit in any position,
    2. Search for at least one upper or lower case in any position,
    3. Enforce password to consist of 6 - 20 characters.
     */
    private final static Pattern PASSWORD_PATTERN = Pattern.compile("^.*(?=.{" + MIN_LENGTH + "," + MAX_LENGTH + "})(?=.*\\d)(?=.*[a-zA-Z]).*$");
    private Matcher matcher;

    @Override
    public void initialize(ValidPassword validPassword) {

    }

    private boolean formatMatch(String s){
        matcher = PASSWORD_PATTERN.matcher(s);
        return matcher.find();
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return formatMatch(s);
    }

}
