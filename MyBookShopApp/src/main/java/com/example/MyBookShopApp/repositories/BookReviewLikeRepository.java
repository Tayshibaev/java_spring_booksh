package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {
    BookReviewLikeEntity getBookReviewLikeEntityByReviewIdAndUserId(BookReviewEntity bookReview, Integer userId);
    List<BookReviewLikeEntity> getBookReviewLikeEntityByReviewId(BookReviewEntity bookReview);
}
