package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, String> {
    Boolean existsTokenBlackListByToken(String token);
}
