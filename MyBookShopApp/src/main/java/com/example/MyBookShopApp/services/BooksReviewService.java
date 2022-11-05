package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.data.book.links.Book2RatingEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.Book2RatingStarsRepository;
import com.example.MyBookShopApp.repositories.BookRatingStarsRepository;
import com.example.MyBookShopApp.repositories.BookReviewRepository;
import com.example.MyBookShopApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BooksReviewService {

    private final UserRepository userRepository;
    private final BookRatingStarsRepository bookRepository;
    private final BookReviewRepository bookReviewRepository;
    private final Book2RatingStarsRepository book2RatingStarsRepository;

    @Autowired
    public BooksReviewService(BookRatingStarsRepository bookRepository, BookReviewRepository bookReviewRepository,
                              Book2RatingStarsRepository book2RatingStarsRepository, UserRepository userRepository
    ) {
        this.bookRepository = bookRepository;
        this.bookReviewRepository = bookReviewRepository;
        this.book2RatingStarsRepository = book2RatingStarsRepository;
        this.userRepository = userRepository;
    }

    public BookReviewEntity saveBookReview(Integer bookId, String text) {
        Integer userId = ThreadLocalRandom.current().nextInt(1, 21);
        BookReviewEntity bookReview = bookReviewRepository.getBookReviewEntityByBookIdAndAndUserId(bookId, userId);
        if (bookReview == null) {
            bookReview = new BookReviewEntity();
            bookReview.setBookId(bookId);
            UserEntity user = userRepository.findById(userId).get();
            bookReview.setUser(user);
        }
        bookReview.setTime(LocalDateTime.now());
        bookReview.setText(text);
        return bookReviewRepository.save(bookReview);
    }

    public BookReviewEntity getBookReviewByReviewId(Integer reviewId) {
        return bookReviewRepository.findById(reviewId).get();
    }

    public Map<BookReviewEntity, Book2RatingEntity> getBookReviewAndBook2RatingByBookId(Integer bookId) {
        List<BookReviewEntity> bookReviews = bookReviewRepository.getAllByBookId(bookId);
        List<Book2RatingEntity> booksRatings = book2RatingStarsRepository.getBook2RatingEntityByBookId(bookId);
//        Map<BookReviewEntity, Book2RatingEntity> resultMap = new HashMap<>();
//        for (BookReviewEntity bb : bookReviews) {
//            Book book = bookRepository.findById(bb.getBookId()).get().getBook();
//            resultMap.put(bb, booksRatings.stream().filter(bok2rat -> bok2rat.getUserId() == bb.getUser().getId())
//                    .findFirst().orElse(new Book2RatingEntity(0, book)));
//        }
        return IntStream.range(0, bookReviews.size())
                .boxed()
                .collect(Collectors.toMap(bookReviews::get,
                        i -> {
                            Book book = bookRepository.findById(bookReviews.get(i).getBookId()).get().getBook();
                            return booksRatings.stream().filter(bok2rat -> bok2rat.getUserId() == bookReviews.get(i).getUser().getId())
                                    .findFirst().orElse(new Book2RatingEntity(0, book));
                        }));

       // return resultMap;
    }


}
