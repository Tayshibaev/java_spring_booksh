package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
