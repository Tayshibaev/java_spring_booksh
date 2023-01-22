package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookPopular;
import com.example.MyBookShopApp.repositories.BookRatingRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BooksRatingAndPopulatityServiceTest {

    private final BooksRatingAndPopulatityService booksRatingAndPopulatityService;

    @Autowired
    BooksRatingAndPopulatityServiceTest(BooksRatingAndPopulatityService booksRatingAndPopulatityService) {
        this.booksRatingAndPopulatityService = booksRatingAndPopulatityService;
    }

    @MockBean
    private BookRatingRepository bookRatingRepositoryMock;

    private final int start = 0;
    private final int finish = 20;

    @Test
    @DisplayName("Проверка сервиса вызова популярных книг. Книги найдены")
    void getRatingPopularBooksTest() {
        List<BookPopular> booksPopular = new ArrayList<>();
        for (int i = start; i < finish; i++) {
            BookPopular bookPopular = new BookPopular();
            bookPopular.setBook(new Book());
            booksPopular.add(bookPopular);
        }
        Page<BookPopular> pagedResponse = new PageImpl(booksPopular);
        Mockito.doReturn(pagedResponse)
                .when(bookRatingRepositoryMock)
                .findBooksRatingDesc(Mockito.any(Pageable.class));
        List<Book> listPopular = booksRatingAndPopulatityService.getRatingPopularBooks(start, finish);
        assertEquals((finish - start), listPopular.size(), "Количество книг: " + (finish - start));
        Mockito.verify(bookRatingRepositoryMock, Mockito.times(1))
                .findBooksRatingDesc(Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Проверка сервиса вызова популярных книг. Книги не найдены")
    void getRatingPopularBooksTestFail() {
        List<BookPopular> booksPopular = new ArrayList<>();
        Page<BookPopular> pagedResponse = new PageImpl(booksPopular);
        Mockito.doReturn(pagedResponse)
                .when(bookRatingRepositoryMock)
                .findBooksRatingDesc(Mockito.any(Pageable.class));
        List<Book> listPopular = booksRatingAndPopulatityService.getRatingPopularBooks(start, finish);
        assertEquals(0, listPopular.size(), "Количество книг: " + 0);
        Mockito.verify(bookRatingRepositoryMock, Mockito.times(1))
                .findBooksRatingDesc(Mockito.any(Pageable.class));
    }
}