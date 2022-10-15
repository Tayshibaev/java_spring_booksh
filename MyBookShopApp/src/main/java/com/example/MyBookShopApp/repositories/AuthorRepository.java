package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author getAuthorBySlug(String slug);
    Author getAuthorById(Integer slug);
}
