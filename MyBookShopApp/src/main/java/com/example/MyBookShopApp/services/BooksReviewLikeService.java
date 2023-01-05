package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.links.Book2RatingEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BooksReviewLikeService {

    private final UserRepository userRepository;
    private final BookRatingStarsRepository bookRepository;
    private final BookReviewLikeRepository bookReviewRepository;
    private final Book2RatingStarsRepository book2RatingStarsRepository;

    @Autowired
    public BooksReviewLikeService(BookRatingStarsRepository bookRepository, BookReviewLikeRepository bookReviewRepository,
                                  Book2RatingStarsRepository book2RatingStarsRepository, UserRepository userRepository
    ) {
        this.bookRepository = bookRepository;
        this.bookReviewRepository = bookReviewRepository;
        this.book2RatingStarsRepository = book2RatingStarsRepository;
        this.userRepository = userRepository;
    }

    public BookReviewLikeEntity saveBookReviewLike(BookReviewEntity bookReviewEntity, Integer userId, String value) {
      //  Integer userId = ThreadLocalRandom.current().nextInt(1, 21);
        BookReviewLikeEntity bookReviewLike = bookReviewRepository.getBookReviewLikeEntityByReviewIdAndUserId(bookReviewEntity, userId);
        if (bookReviewLike == null) {
            bookReviewLike = new BookReviewLikeEntity();
            bookReviewLike.setReviewId(bookReviewEntity);
            bookReviewLike.setUserId(userId);
        }
        bookReviewLike.setTime(LocalDateTime.now());
        bookReviewLike.setValue(Short.parseShort(value));

        return bookReviewRepository.save(bookReviewLike);
    }


}
