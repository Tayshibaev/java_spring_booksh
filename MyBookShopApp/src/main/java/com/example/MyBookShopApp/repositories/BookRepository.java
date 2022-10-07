package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByAuthor_FirstName(String name);

    @Query("from Book")
    List<Book> customFindAllBooks();

    //NEW BOOK REST REPOSITORY COMMANDS

    List<Book> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    //Новинки будут выводиться в порядке убывания по дате
    @Query(value = "from Book b order by b.pubDate desc")
    Page<Book> findAllOrderedDate(Pageable nextPage);

    //Новинки будут выводиться в порядке убывания по дате
    @Query(value = "from Book b where b.pubDate >= ?1 and b.pubDate<= ?2 order by b.pubDate desc")
    Page<Book> findAllFromToDateOrderedDate(Date fromm, Date to, Pageable nextPage);

    //Заглушка для популярных
    @Query(value = "from Book b order by b.isBestseller desc, b.pubDate desc")
    Page<Book> findAllOrderedIsBestsellerAndDate(Pageable nextPage);

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);
}
