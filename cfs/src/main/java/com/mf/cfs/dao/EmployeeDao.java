package com.mf.cfs.dao;

import com.mf.cfs.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);
    Employee findOne(int id);
    Employee findEmployeeByIdAndPassword(int id, String password);
}
