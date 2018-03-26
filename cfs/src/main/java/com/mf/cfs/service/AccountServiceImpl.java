package com.mf.cfs.service;

import com.mf.cfs.dao.CustomerDao;
import com.mf.cfs.dao.EmployeeDao;
import com.mf.cfs.dao.FundPriceHistoryDao;
import com.mf.cfs.dao.PositionDao;
import com.mf.cfs.domain.*;
import com.mf.cfs.form.CreateCustomerForm;
import com.mf.cfs.form.CreateEmployeeForm;
import com.mf.cfs.form.RegularPasswordForm;
import com.mf.cfs.form.SpecialPasswordForm;
import com.mf.cfs.util.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.mf.cfs.util.Error.ALREADY_REGISTERED_CUSTOMER;

@Service(value = "AccountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private FundPriceHistoryDao fundPriceHistoryDao;

    @Autowired
    private  FundService fundService;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean createCustomerAccount(CreateCustomerForm form) {
        Customer customer = new Customer();
        Boolean isSuccess;
        if ((customerDao.findByUsername(form.getEmail()) == null)&&
                (employeeDao.findByUsername(form.getEmail()) == null)){
            customer = formToCustomerDomain(form,customer);
            customerDao.save(customer);
            isSuccess = true;
        } else{
            throw new RuntimeException(Error.ALREADY_REGISTERED_CUSTOMER.getError());
        }
        return isSuccess;
    }

    private Customer formToCustomerDomain(CreateCustomerForm form, Customer customer){
        customer.setUsername(form.getEmail());
        customer.setPassword(encoder.encode(form.getPassword()));
        customer.setFirstName(form.getFirstname());
        customer.setLastName(form.getLastname());
        customer.setAddrLine1(form.getAddr_line1());
        customer.setAddrLine2(form.getAddr_line2());
        customer.setCity(form.getCity());
        customer.setState(form.getState());
        customer.setZip(form.getZip());
        return customer;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean createEmployeeAccount(CreateEmployeeForm form) {
        Employee employee = new Employee();
        boolean isSuccess;
        // email to be registered does not exist in both customer and employee tables
        if ((customerDao.findByUsername(form.getEmail()) == null)&&
                (employeeDao.findByUsername(form.getEmail()) == null)){
            employee = formToEmployeeDomain(form, employee);
            employeeDao.save(employee);
            isSuccess = true;
        } else{
            throw new RuntimeException(Error.ALREADY_REGISTERED_EMPLOYEE.getError());
        }
        return isSuccess;
    }

    private Employee formToEmployeeDomain(CreateEmployeeForm form, Employee employee){
        employee.setUsername(form.getEmail());
        employee.setFirstname(form.getFirstname());
        employee.setLastname(form.getLastname());
        employee.setPassword(encoder.encode(form.getPassword()));
        return employee;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean changePassWdByCustomer(RegularPasswordForm form, int id) {
        boolean isSuccess;
        Customer customer = customerDao.findOne(id);
        if (customer != null){
            if(encoder.matches(form.getCurrentPassWd(), customer.getPassword())){
                customer.setPassword(encoder.encode(form.getNewPassWd()));
                customerDao.save(customer);
                isSuccess = true;
            } else {
                throw new RuntimeException(Error.WRONG_PASSWORD.getError());
            }
        }else {
            throw new RuntimeException(Error.WRONG_PASSWORD.getError());
        }
        return isSuccess;

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean changePassWdByEmployee(RegularPasswordForm form, int id) {
        boolean isSuccess;
        Employee employee = employeeDao.findOne(id);
        System.out.println(employee.getLastname());
        if (employee != null){
            if (encoder.matches(form.getCurrentPassWd(), employee.getPassword())){
                employee.setPassword(encoder.encode(form.getNewPassWd()));
                employeeDao.save(employee);
                isSuccess = true;
            } else{
                throw new RuntimeException(Error.WRONG_PASSWORD.getError());
            }
        } else {
           throw new RuntimeException(Error.WRONG_PASSWORD.getError());
        }
        return isSuccess;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean changeCustomerPassWdByEmployee(SpecialPasswordForm form) {
        boolean isSuccess;
        Customer customer = customerDao.getOne(form.getUserID());
        if (customer != null){
            customer.setPassword(encoder.encode(form.getPassword()));
            customerDao.save(customer);
            isSuccess = true;
        } else {
            throw new RuntimeException(Error.INVALID_CUSTOMER_ID.getError());
        }
        return isSuccess;

    }

    @Override
    public List<Customer> getCustomerList() {
        return customerDao.findAll();
    }

    @Override
    public CustomerDetail getCustomerDetails(String username) {
        return null;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public List<Position> getPositions(int id) {
        System.out.println("id" + id);
        List<Position> positions = positionDao.findByIdCustomer(customerDao.findOne(id));
        return positions;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<PositionForDisplay> getPositionForDisplays(int id) {
        List<Position> positions = getPositions(id);
        List<PositionForDisplay> positionForDisplays = new LinkedList<>();
        Date lastday = fundPriceHistoryDao.getMaxDate();
        for (Position p : positions) {
            double price = fundService.getPrice(lastday, p.getId().getFund());
            PositionForDisplay pd = new PositionForDisplay();
            pd.setFund(p.getId().getFund());
            pd.setShares(p.getShares());
            pd.setPrice(price);
            pd.setValue(price * p.getShares());
            positionForDisplays.add(pd);
        }
        return positionForDisplays;
    }

    @Override
    public double getCash(int id) {
        double cash = customerDao.findOne(id).getCash();
        return cash;
    }

    @Override
    public Customer findCustomer(int id) {
        return customerDao.getOne(id);
    }

    // Locates the user based on the username.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Customer customer = customerDao.findByUsername(username);
        Employee employee = employeeDao.findByUsername(username);
        String role;
        Set<GrantedAuthority> authorities = new HashSet<>();
        User user;

        if (customer == null && employee == null) {
            throw new UsernameNotFoundException("No such user");
        }
        if (customer != null) {
            role = "Customer";
            authorities.add(new SimpleGrantedAuthority(role));
            user = new User(Integer.toString(customer.getId()), customer.getPassword(), authorities);

        } else {
            role = "Employee";
            authorities.add(new SimpleGrantedAuthority(role));
            user = new User(Integer.toString(employee.getId()), employee.getPassword(), authorities);
        }

        return user;

    }
}
