package com.mf.cfs.validation;

import com.mf.cfs.form.CreateCustomerForm;
import com.mf.cfs.form.CreateEmployeeForm;
import com.mf.cfs.form.RegularPasswordForm;
import com.mf.cfs.form.SpecialPasswordForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingPasswordValidator implements ConstraintValidator<ValidMatchingPassword, Object> {
    @Override
    public void initialize(ValidMatchingPassword validMatchingPassword) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof CreateCustomerForm){
            CreateCustomerForm form = (CreateCustomerForm)o;
            return form.getPassword().equals(form.getMatchingPassword());
        }
        if (o instanceof CreateEmployeeForm){
            CreateEmployeeForm form = (CreateEmployeeForm)o;
            return form.getPassword().equals(form.getMatchingPassword());
        }
        if (o instanceof RegularPasswordForm){
            RegularPasswordForm form = (RegularPasswordForm)o;
            return form.getNewPassWd().equals(form.getMatchingPassWd());
        }
        if (o instanceof SpecialPasswordForm){
            SpecialPasswordForm form = (SpecialPasswordForm)o;
            return form.getPassword().equals(form.getMatchingPassword());
        }
        return false;
    }
}
