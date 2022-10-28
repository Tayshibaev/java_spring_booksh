package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookPopular;
import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.data.book.links.Book2RatingEntity;
import com.example.MyBookShopApp.repositories.Book2RatingStarsRepository;
import com.example.MyBookShopApp.repositories.BookRatingRepository;
import com.example.MyBookShopApp.repositories.BookRatingStarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<BookRatingStars> bookStr = bookRepository.findBooksRatingDesc(id);
        return bookStr.orElseGet(() -> new BookRatingStars(id));
    }

    public void saveBook2Rating(Book book, String value) {
        Book2RatingEntity book2Rating = new Book2RatingEntity(Integer.parseInt(value), book);
        book2Rating.setId(book2RatingStarsRepository.findAll().size() + 1);
        book2RatingStarsRepository.save(book2Rating);
    }

}
