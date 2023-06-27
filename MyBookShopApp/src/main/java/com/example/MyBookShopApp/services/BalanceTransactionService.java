package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.BalanceTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceTransactionService {

    private BalanceTransactionRepository balanceTransactionRepository;

    @Autowired
    public BalanceTransactionService(BalanceTransactionRepository balanceTransactionRepository) {
        this.balanceTransactionRepository = balanceTransactionRepository;
    }

    public List<BalanceTransactionEntity> getTransactionsDesc(UserEntity user, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return balanceTransactionRepository.findAllByUserIdOrderByTimeDesc(user.getId(), nextPage).getContent();
    }

    public List<BalanceTransactionEntity> getTransactionsAsc(UserEntity user, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return balanceTransactionRepository.findAllByUserIdOrderByTimeAsc(user.getId(), nextPage).getContent();
    }
}
