package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookPopular;
import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.repositories.BookRatingStarsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BookServiceTest {

    private final BookService bookService;

    @MockBean
    private BookRatingStarsRepository bookRatingStarsRepositoryMock;

    @Autowired
    public BookServiceTest(BookService bookService) {
        this.bookService = bookService;
    }

    private final int offset = 0;
    private final int limit = 6;

    @Test
    @DisplayName("Проверка сервиса по вызову списка рекомендованных книг")
    void getPageOfRecommendedBooks() {
        List<BookRatingStars> books = new ArrayList<>();
        for (int i = offset; i < limit; i++) {
            Book book = new Book();
            BookRatingStars bookStr = new BookRatingStars();
            bookStr.setBook(book);
            books.add(bookStr);
        }
        Page<BookPopular> pagedResponse = new PageImpl(books);
        Mockito.doReturn(pagedResponse)
                .when(bookRatingStarsRepositoryMock)
                .findBooksRatingDesc(Mockito.any(Pageable.class));
        List<Book> booksRecom = bookService.getPageOfRecommendedBooks(offset, limit);
        Assertions.assertEquals(limit - offset, booksRecom.size(),
                "Количество возвращенных книг равно " + (limit - offset));
        Mockito.verify(bookRatingStarsRepositoryMock, Mockito.times(1))
                .findBooksRatingDesc(Mockito.any(Pageable.class));

    }
}