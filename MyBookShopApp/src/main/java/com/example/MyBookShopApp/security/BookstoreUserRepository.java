package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreUserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findBookstoreUserByEmail(String email);
    UserEntity findBookstoreUserByPhone(String phone);
}
