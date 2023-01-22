package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.repositories.Book2UserRepository;
import com.example.MyBookShopApp.repositories.BookRatingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRatingAndPopulatityRepositoryTests {

    private final Book2UserRepository book2UserRepository;
    private final BookRatingRepository bookRatingRepository;

    @Autowired
    public BookRatingAndPopulatityRepositoryTests(Book2UserRepository book2UserRepository, BookRatingRepository bookRatingRepository) {
        this.book2UserRepository = book2UserRepository;
        this.bookRatingRepository = bookRatingRepository;
    }

    @Test
    @DisplayName("Проверка количество возвращаемых популярных книг и сортировка по популярности")
    void countPopularBooksAndSorting() {
        int start = 0;
        int finish = 40;
        Pageable nextPage = PageRequest.of(start, finish);
        List<BookPopular> bookPopulars = bookRatingRepository.findBooksRatingDesc(nextPage).getContent();
        assertEquals(finish, bookPopulars.size(), "Количество возвращаемых книг равно: " + finish);
        List<BookPopular> bookPopularsSorted = bookPopulars.stream().sorted(
                Comparator.comparing(BookPopular::getRating).reversed()
        ).collect(Collectors.toList());
        System.out.println(bookPopularsSorted);
        assertEquals(bookPopularsSorted, bookPopulars, "Книги из БД отсортированы по рейтингу популярности");
    }

    @Test
    @DisplayName("Проверка расчета рейтинга популярности книги")
    void checkFormula() {
        int start = 0;
        int finish = 1;
        Pageable nextPage = PageRequest.of(start, finish);
        BookPopular bookPopular = bookRatingRepository.findBooksRatingDesc(nextPage).getContent().stream().findFirst().get();
        List<Book2UserEntity> books2Users = book2UserRepository.getByBookId(bookPopular.getBook());
        long kept = books2Users.stream().filter(x -> x.getTypeId() == 1).count();
        long cart = books2Users.stream().filter(x -> x.getTypeId() == 2).count();
        long paid = books2Users.stream().filter(x -> x.getTypeId() == 3).count();
        double rating = new BigDecimal(paid + 0.7 * cart + 0.4 * kept).setScale(2, RoundingMode.HALF_UP).doubleValue();
        assertEquals(rating, bookPopular.getRating(),"Рейтинг из метода по расчету из рейтинга из БД равен рассчитаному " +
                "рейтингу: " + rating);
    }

}