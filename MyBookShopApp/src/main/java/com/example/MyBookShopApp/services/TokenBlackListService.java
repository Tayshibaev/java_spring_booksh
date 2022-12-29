package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.TokenBlackList;
import com.example.MyBookShopApp.repositories.TokenBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlackListService {

    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    public Boolean isTokenInBlackList(String token) {
        return tokenBlackListRepository.existsTokenBlackListByToken(token);
    }

    public String addTokenInBlackList(String token) {
        tokenBlackListRepository.save(new TokenBlackList(token));
        return token;
    }
}
