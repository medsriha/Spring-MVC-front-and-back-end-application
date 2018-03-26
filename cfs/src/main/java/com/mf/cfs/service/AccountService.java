package com.mf.cfs.service;

import com.mf.cfs.domain.*;
import com.mf.cfs.form.CreateCustomerForm;
import com.mf.cfs.form.CreateEmployeeForm;
import com.mf.cfs.form.RegularPasswordForm;
import com.mf.cfs.form.SpecialPasswordForm;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;

public interface AccountService extends UserDetailsService{
//    String getAccountIDAfterLogin();
    boolean createCustomerAccount(CreateCustomerForm form);
    boolean createEmployeeAccount(CreateEmployeeForm form);
    boolean changePassWdByCustomer(RegularPasswordForm form, int id);
    boolean changePassWdByEmployee(RegularPasswordForm form, int id);
    boolean changeCustomerPassWdByEmployee(SpecialPasswordForm form);
    List<Customer> getCustomerList();
    CustomerDetail getCustomerDetails(String username);
    boolean logout();
    List<Position> getPositions(int id);
    double getCash(int id);
    Customer findCustomer(int id);
    List<PositionForDisplay> getPositionForDisplays(int id);

}
