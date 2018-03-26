package com.mf.cfs.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipcodeValidator implements ConstraintValidator<ValidZipCode, String> {
    private final static Integer LENGTH = 5;
    private final static Pattern ZIP_CODE_PATTERN = Pattern.compile("[0-9]+");
    private Matcher matcher;

    @Override
    public void initialize(ValidZipCode validZipCode) {

    }

    private boolean formatMatch(String s){
        matcher = ZIP_CODE_PATTERN.matcher(s);
        return (matcher.find()) && (s.length() == LENGTH);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return formatMatch(s);
    }
}
