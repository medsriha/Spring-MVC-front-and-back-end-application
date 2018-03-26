package com.mf.cfs.service;

import com.mf.cfs.dao.*;
import com.mf.cfs.domain.*;
import com.mf.cfs.form.BuyFundForm;
import com.mf.cfs.form.DepositCheckForm;
import com.mf.cfs.form.RequestCheckForm;
import com.mf.cfs.form.SellFundForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mf.cfs.domain.Transaction_type.*;

@Service(value = "TransactionService")

public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private FundDao fundDao;

    @Autowired
    private LastDateDao lastDateDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private FundService fundService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private FundPriceHistoryDao fund_price_historyDao;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createData() {

        Customer customer = new Customer();
        customer.setUsername("test2@cfs.com");
        customer.setCash(10000.0);
        customer.setAvailableCash(10000.0);
        customer.setCity("Pit");
        customer.setFirstName("John");
        customer.setAddrLine1("line1");
        customer.setAddrLine2("line2");
        customer.setLastName("Doe");
        customer.setPassword(encoder.encode("123"));
        customer.setZip("15213");
        customer.setState("PA");
        customer.setRole("Customer");
        customerDao.save(customer);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse("26/01/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Fund fund = new Fund();
        fund.setName("Test fund2");
        fund.setSymbol("TEST2");
        fund.setCreated_at(d);
        fund.setPrice(56.0);
        fundDao.save(fund);

        Position position = new Position();
        position.setId(new PositionCompPK(customer,fund));
        position.setShares(100);
        positionDao.save(position);

        Fund_Price_History fph = new Fund_Price_History();
        fph.setId(new FundDateCompPK(fundDao.findOne(1), new Date()));
        fph.setPrice(56);
        fund_price_historyDao.save(fph);

        Employee employee = new Employee();
        employee.setFirstname("Jeff");
        employee.setLastname("Eppinger");
        employee.setPassword(encoder.encode("je123456"));
        employee.setRole("Employee");
        employee.setUsername("jle@cs.cmu.edu");
        employeeDao.save(employee);

        LastDate lastDate = new LastDate();
        lastDate.setLastDate(d);
        lastDateDao.save(lastDate);
    }

    @Override
    public double getCash(int id) {
        return customerDao.getOne(id).getCash();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean hasSufficientCash(Customer customer, double amount, BindingResult result) {
        if (customer == null) {
            return false;
        }
        if (amount * 100 - ((Double) (amount * 100)).intValue() > 0) {
            result.addError(new FieldError("form", "amount", "The smallest unit is $0.01."));
            return false;
        }

        if (amount > customer.getAvailableCash()) {
            result.addError(new FieldError("form", "amount", "You don't have sufficient cash."));
            return false;
        }
        return true;
    }
    private boolean isSellValid(Position position, SellFundForm form, BindingResult result) {
        // check whether the fund belongs to customer
        if (position == null) {
            result.addError(new ObjectError("sellForm", "Your request didn't go through."));
            return false;
        }
        double shares = form.getShares();
        if (shares * 1000 - ((Double) (shares * 1000)).intValue() > 0) {
            result.addError(new FieldError("form", "amount", "The smallest unit is 0.001 share."));
            return false;
        }
        double upbound = position.getShares();
        if (shares > upbound) {
            result.addError(new FieldError("sellForm", "shares", "You don't have enough shares"));
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidFund(Fund fund, BindingResult result) {

        if (fund == null) {
            result.addError(new FieldError("sellForm", "fund_id", "Please select a valid fund."));
            return false;
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction buyFund(int id, BuyFundForm form, BindingResult result) {
        Customer customer = customerDao.findOne(id);
        double amount = form.getAmount();
        int fund_id = form.getFund_id();
        Fund fund = fundDao.findOne(fund_id);

        if (!hasSufficientCash(customer, amount, result) || !isValidFund(fund, result) || result.hasErrors()) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCustomer(customer);
        transaction.setFund(fund);
        transaction.setTransaction_type(buyFund);
        transactionDao.save(transaction);

        customer.setAvailableCash(customer.getAvailableCash() - amount);
        customerDao.save(customer);

        return transaction;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction depositCheck(DepositCheckForm depositCheckForm, BindingResult result) {
        Customer customer = customerDao.findByUsername(depositCheckForm.getUsername());
        if (result.hasErrors()) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(depositCheckForm.getAmount());
        transaction.setCustomer(customer);
        transaction.setTransaction_type(depositCheck);
        transactionDao.save(transaction);
        return transaction;

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction sellFund(int customer_id, SellFundForm form, BindingResult result) {
        Customer customer = customerDao.findOne(customer_id);
        Fund fund = fundDao.findOne(form.getFund_id());
        Position position = positionDao.findById(new PositionCompPK(customer, fund));
        if (!isSellValid(position, form, result) || result.hasErrors()) {
            System.out.println("sell fund error");
            return null;
        }
        double newShareCount = position.getShares() - form.getShares();
        if(newShareCount == 0.0)    {
            positionDao.delete(position);
        }   else    {
            position.setShares(position.getShares() - form.getShares());
            positionDao.save(position);
        }
        Transaction transaction = new Transaction();
        transaction.setFund(fund);
        transaction.setShares(form.getShares());
        transaction.setCustomer(customer);
        transaction.setTransaction_type(sellFund);
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction requestCheck(int id, RequestCheckForm form, BindingResult result) {

        Customer customer = customerDao.findOne(id);
        double amount = form.getAmount();
        if (!hasSufficientCash(customer, amount, result) || result.hasErrors()) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCustomer(customer);
        transaction.setTransaction_type(requestCheck);
        transactionDao.save(transaction);

        customer.setAvailableCash(customer.getAvailableCash() - amount);
        customerDao.save(customer);

        return transaction;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void approveCheckDeposit(Date date, Transaction transaction) {
        Customer customer = transaction.getCustomer();
        customer.setCash(customer.getCash() + transaction.getAmount());
        customer.setAvailableCash(customer.getAvailableCash() + transaction.getAmount());
        transaction.setExecuteDate(date);
        customerDao.save(customer);
        transactionDao.save(transaction);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void approveSellFund(Date date, Transaction transaction) {
        System.out.println("find");
        System.out.println(date);
        Customer customer = transaction.getCustomer();
        transaction.setExecuteDate(date);
        double amount = transaction.getShares() * fundService.getPrice(date, transaction.getFund());
        transaction.setAmount(amount);
        customer.setCash(customer.getCash() + amount);
        customer.setAvailableCash(customer.getAvailableCash() + transaction.getAmount());
        transactionDao.save(transaction);
        customerDao.save(customer);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void approveCheckRequest(Date date, Transaction transaction) {
        Customer customer = transaction.getCustomer();
        customer.setCash(customer.getCash() - transaction.getAmount());
        transaction.setExecuteDate(date);
        transactionDao.save(transaction);
        customerDao.save(customer);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void approveBuyFund(Date date, Transaction transaction) {
        Customer customer = transaction.getCustomer();
        Fund fund = transaction.getFund();
        double amount = transaction.getAmount();
        Position position = positionDao.findById(new PositionCompPK(customer, fund));

        // Calculate new position
        double shares = amount / fundService.getPrice(date, fund);

        // Update transaction and position
        transaction.setShares(shares);
        transaction.setExecuteDate(date);
        if (position != null) {
            position.setShares(position.getShares() + shares);
            positionDao.save(position);
        } else {
            Position nposition = new Position();
            nposition.setShares(shares);
            nposition.setId(new PositionCompPK(customer, fund));
            positionDao.save(nposition);
        }
        customer.setCash(customer.getCash() - amount);
        transactionDao.save(transaction);
        customerDao.save(customer);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<TransactionForDisplay> getTransactions(int id) {
        Customer customer = customerDao.findOne(id);
        List<Transaction> transactions = transactionDao.findTransactionsByCustomer(customer);
        List<TransactionForDisplay>  transactionHistory= new ArrayList<>();
        for (Transaction t: transactions) {
            TransactionForDisplay nt = new TransactionForDisplay();
            nt.setId(t.getTransaction_id());
            nt.setType(t.getTransaction_type().toString());
            nt.setAmount(t.getAmount());
            nt.setName(t.getFund() == null ? "": t.getFund().getName());
            nt.setStatus(t.getExecuteDate());
            nt.setShares(t.getShares());
            nt.setAmount(t.getAmount());
            nt.setPrice(t.getShares() == 0 ? 0: t.getAmount()/t.getShares());
            transactionHistory.add(nt);
        }
        return transactionHistory;
    }



}
