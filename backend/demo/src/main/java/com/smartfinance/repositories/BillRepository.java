package com.smartfinance.repositories;

import com.smartfinance.entities.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Page<Bill> findByUserIdAndStatus(Long userId, Bill.Status status , Pageable pageable);

    Page<Bill> findByUserId(Long userId, Pageable pageable);

    int countByUserIdAndStatus(Long userId, Bill.Status status);
}
