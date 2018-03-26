package com.mf.cfs.dao;

import com.mf.cfs.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Customer findByUsername(String username);

    @Override
    List<Customer> findAll();

    Customer findOne(int id);

    Customer findCustomerByIdAndPassword(int id, String passWd);
}
