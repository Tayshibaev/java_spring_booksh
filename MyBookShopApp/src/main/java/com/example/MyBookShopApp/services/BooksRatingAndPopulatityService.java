package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookPopular;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.repositories.BookRatingRepository;
import com.example.MyBookShopApp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class BooksRatingAndPopulatityService {

    private BookRatingRepository bookRepository;

    @Autowired
    public BooksRatingAndPopulatityService(BookRatingRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Получить книги по рейтингу
    public List<Book> getRatingPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksRatingDesc(nextPage).getContent()
                .stream().map(BookPopular::getBook).collect(Collectors.toList());
    }


}
