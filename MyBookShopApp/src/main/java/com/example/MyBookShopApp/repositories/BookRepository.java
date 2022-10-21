package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
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

    @Query(value = "select b.* from book2tag bt " +
            "join books b on bt.book_id = b.id " +
            " where bt.tag_id = ?1 order by b.title", nativeQuery = true)
    Page<Book> getBooksByTagId(Long idTag, Pageable nextPage);

    @Query(value = "select b.* from book2tag bt " +
            "join books b on bt.book_id = b.id " +
            "join tags ts on bt.tag_id = ts.id " +
            " where ts.name = ?1 order by b.title", nativeQuery = true)
    Page<Book> getBooksByTagName(String name, Pageable nextPage);

    @Query(value = "select b.* from books b " +
            "join book2genre bg on b.id = bg.book_id " +
            "join genre gg on gg.id = bg.genre_id " +
            "where gg.id = ?1", nativeQuery = true)
    Page<Book> getBookByGenreId(Integer idGenre, Pageable nextPage);

    @Query(value = "select b.* from books b " +
            "join authors au on b.author_id = au.id where au.id = ?1", nativeQuery = true)
    Page<Book> getBookByAuthorId(Integer id, Pageable nextPage);

    Book findBookBySlug(String slug);

    List<Book> findBooksBySlugIn(String[] slugs);
}
