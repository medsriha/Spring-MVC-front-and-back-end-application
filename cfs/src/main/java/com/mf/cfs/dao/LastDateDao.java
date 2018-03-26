package com.mf.cfs.dao;

import com.mf.cfs.domain.LastDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface LastDateDao extends JpaRepository<LastDate, Integer> {
}
