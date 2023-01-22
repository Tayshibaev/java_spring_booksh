package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.repositories.BookRatingStarsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRatingStarsRepositoryTest {

    private final BookRatingStarsRepository bookRatingStarsRepository;

    @Autowired
    public BookRatingStarsRepositoryTest(BookRatingStarsRepository bookRatingStarsRepository) {
        this.bookRatingStarsRepository = bookRatingStarsRepository;
    }

    @Test
    @DisplayName("Проверка запроса в БД для выборки рекомендованных книг по рейтингу")
    void findBooksRatingDescTest() {
        int start = 0;
        int finish = 6;
        Pageable nextPage = PageRequest.of(start, finish);

        List<BookRatingStars> recommendedBooks = bookRatingStarsRepository.findBooksRatingDesc(nextPage).getContent();
        Assertions.assertEquals(finish - start, recommendedBooks.size(),
                "Количество книг равно " + (finish - start));
        List<BookRatingStars> recNew = recommendedBooks.stream()
                .sorted(Comparator.comparing(BookRatingStars::getRatingRange).reversed()
        ).collect(Collectors.toList());
        Assertions.assertEquals(recNew, recommendedBooks);
    }

}