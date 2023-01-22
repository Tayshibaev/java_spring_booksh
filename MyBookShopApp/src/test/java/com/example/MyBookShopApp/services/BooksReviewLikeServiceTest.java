package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.repositories.BookReviewLikeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

//Тесты по расчету рейинга комментариев
@SpringBootTest
class BooksReviewLikeServiceTest {

    private final BooksReviewLikeService booksReviewLikeService;

    @Autowired
    BooksReviewLikeServiceTest(BooksReviewLikeService booksReviewLikeService) {
        this.booksReviewLikeService = booksReviewLikeService;
    }

    private final long likeCount = 7;
    private final long dislikeCount = 3;

    @MockBean
    private BookReviewLikeRepository bookReviewRepositoryMock;

    @Test
    @DisplayName("Проверка расчета ретинга комментария")
    void getRatingReviewTest() {
        List<BookReviewLikeEntity> bookReviewLikeEntities = new ArrayList<>();
        for (int i = 0; i < likeCount; i++) {
            BookReviewLikeEntity bookReviewLike = new BookReviewLikeEntity();
            bookReviewLike.setValue((short) 1);
            bookReviewLikeEntities.add(bookReviewLike);
        }

        for (int i = 0; i < dislikeCount; i++) {
            BookReviewLikeEntity bookReviewLike = new BookReviewLikeEntity();
            bookReviewLike.setValue((short) -1);
            bookReviewLikeEntities.add(bookReviewLike);
        }

        Mockito.doReturn(bookReviewLikeEntities)
                .when(bookReviewRepositoryMock)
                .getBookReviewLikeEntityByReviewId(Mockito.any(BookReviewEntity.class));
        long rating = booksReviewLikeService.getRatingReview(new BookReviewEntity());
        Assertions.assertEquals(likeCount-dislikeCount, rating, "Рейтинг высчитывается верно");
        Mockito.verify(bookReviewRepositoryMock, Mockito.times(1))
                .getBookReviewLikeEntityByReviewId(Mockito.any(BookReviewEntity.class));
    }

    @Test
    @DisplayName("Проверка расчета ретинга комментария. Оценок у комментария нет")
    void getRatingReviewNoRatingTest() {
        List<BookReviewLikeEntity> bookReviewLikeEntities = new ArrayList<>();
        Mockito.doReturn(bookReviewLikeEntities)
                .when(bookReviewRepositoryMock)
                .getBookReviewLikeEntityByReviewId(Mockito.any(BookReviewEntity.class));
        long rating = booksReviewLikeService.getRatingReview(new BookReviewEntity());
        Assertions.assertEquals(0, rating, "Рейтинг высчитывается верно и равен 0");
        Mockito.verify(bookReviewRepositoryMock, Mockito.times(1))
                .getBookReviewLikeEntityByReviewId(Mockito.any(BookReviewEntity.class));
    }

}