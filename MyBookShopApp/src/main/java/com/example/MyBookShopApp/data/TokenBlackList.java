package com.example.MyBookShopApp.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TokenBlackList {

    public TokenBlackList() {
    }

    public TokenBlackList(String token) {
        this.token = token;
    }

    @Id
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
