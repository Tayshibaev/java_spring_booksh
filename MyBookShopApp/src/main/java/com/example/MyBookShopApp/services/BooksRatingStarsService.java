package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.data.book.links.Book2RatingEntity;
import com.example.MyBookShopApp.repositories.Book2RatingStarsRepository;
import com.example.MyBookShopApp.repositories.BookRatingStarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BooksRatingStarsService {

    private BookRatingStarsRepository bookRepository;
    private Book2RatingStarsRepository book2RatingStarsRepository;

    @Autowired
    public BooksRatingStarsService(BookRatingStarsRepository bookRepository, Book2RatingStarsRepository book2RatingStarsRepository) {
        this.bookRepository = bookRepository;
        this.book2RatingStarsRepository = book2RatingStarsRepository;
    }

    //Получить книги по рейтингу
    public BookRatingStars getRatingPopularBooks(Integer id) {
        Optional<BookRatingStars> bookStr = bookRepository.findBookRatingByBookId(id);
        return bookStr.orElseGet(() -> new BookRatingStars(id));
    }

    public void saveBook2Rating(Book book, Integer userId, String value) {
//        Integer userId = ThreadLocalRandom.current().nextInt(1, 20);

        Book2RatingEntity book2Rating = book2RatingStarsRepository.getBook2RatingEntityByBookIdAndUserId(book, userId);
        if (book2Rating == null) {
            book2Rating = new Book2RatingEntity(Integer.parseInt(value), book);
            book2Rating.setUserId(userId);//Рандоный юзер
        } else {
            book2Rating.setRating(Integer.parseInt(value));
        }
        book2RatingStarsRepository.save(book2Rating);
    }

}
