package com.mf.cfs.domain;
import com.mf.cfs.form.CreateCustomerForm;
import com.mf.cfs.form.CreateFundForm;
import com.mf.cfs.form.SetTransitionDateForm;

import java.util.List;

public class EmployeeDetail {
    private List<Customer> customerList;
    private CreateCustomerForm createCustomerForm;
    private CreateFundForm createFundForm;
    private SetTransitionDateForm setTransitionDateForm;
    private Employee employee;

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public CreateCustomerForm getCreateCustomerForm() {
        return createCustomerForm;
    }

    public void setCreateCustomerForm(CreateCustomerForm createCustomerForm) {
        this.createCustomerForm = createCustomerForm;
    }

    public CreateFundForm getCreateFundForm() {
        return createFundForm;
    }

    public void setCreateFundForm(CreateFundForm createFundForm) {
        this.createFundForm = createFundForm;
    }

    public SetTransitionDateForm getSetTransitionDateForm() {
        return setTransitionDateForm;
    }

    public void setSetTransitionDateForm(SetTransitionDateForm setTransitionDateForm) {
        this.setTransitionDateForm = setTransitionDateForm;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
