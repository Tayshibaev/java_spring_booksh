package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Integer> {
    BookReviewEntity getBookReviewEntityByBookIdAndAndUserId(Integer bookId, Integer userId);
    List<BookReviewEntity> getAllByBookId(Integer bookId);

//    @Query
//    List<BookReviewAndRatingEntity> getBooksReviewAndRatingByBookId();
}
