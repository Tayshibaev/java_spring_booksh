package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.data.book.links.Book2RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Book2RatingStarsRepository extends JpaRepository<Book2RatingEntity, Integer> {
}
