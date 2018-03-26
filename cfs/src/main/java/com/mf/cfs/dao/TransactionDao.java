package com.mf.cfs.dao;

import com.mf.cfs.domain.Customer;
import com.mf.cfs.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByExecuteDateIsNull();
    List<Transaction> findTransactionsByCustomer(Customer customer);
}
