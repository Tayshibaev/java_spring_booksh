package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.data.book.links.Book2RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Book2RatingStarsRepository extends JpaRepository<Book2RatingEntity, Integer> {
    @Query(value = "from Book2RatingEntity b where b.bookId.id = ?1")
    List<Book2RatingEntity> getBook2RatingEntityByBookId(Integer bookId);
    Book2RatingEntity getBook2RatingEntityByBookIdAndUserId(Book bookId, Integer userId);
}
