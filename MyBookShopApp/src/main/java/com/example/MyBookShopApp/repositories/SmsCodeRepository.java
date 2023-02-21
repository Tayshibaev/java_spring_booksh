package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    public SmsCode findByCode(String code);
}
