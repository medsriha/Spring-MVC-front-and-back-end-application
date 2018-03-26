package com.mf.cfs.dao;

import com.mf.cfs.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PositionDao extends JpaRepository<Position, Integer> {
    List<Position> findByIdCustomer(Customer customer);
    Position findById(PositionCompPK id);
}
