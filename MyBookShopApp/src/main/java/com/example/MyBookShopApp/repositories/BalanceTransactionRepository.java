package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransactionEntity, Integer> {
    Page<BalanceTransactionEntity> findAllByUserIdOrderByTimeDesc(Integer id, Pageable nextPage);
    Page<BalanceTransactionEntity> findAllByUserIdOrderByTimeAsc(Integer id, Pageable nextPage);
}
