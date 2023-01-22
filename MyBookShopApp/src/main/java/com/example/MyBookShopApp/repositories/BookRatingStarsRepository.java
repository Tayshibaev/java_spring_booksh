package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.BookRatingStars;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRatingStarsRepository extends JpaRepository<BookRatingStars, Integer> {

    @Query(value = "select bu.id as book_id, COALESCE(A.cc, 0) as one, COALESCE(B.cc, 0) as two," +
            "COALESCE(C.cc, 0) as three,COALESCE(D.cc, 0) as four," +
            "COALESCE(E.cc, 0) as five, (3*COALESCE(C.cc, 0)+ 2*COALESCE(B.cc, 0)+ COALESCE(A.cc, 0)+" +
            " 4*COALESCE(D.cc, 0)+5*COALESCE(E.cc, 0))/" +
            " (COALESCE(C.cc, 0)+ COALESCE(B.cc, 0)+ COALESCE(A.cc, 0)+" +
            " COALESCE(D.cc, 0)+COALESCE(E.cc, 0)) as ratings " +
            "from books as bu " +
            "left join (select bookid,count(*) as cc from book2rating " +
            "where rating = '1' " +
            "group by bookid) as A on A.bookid = bu.id " +
            "left join (select bookid,count(*) as cc from book2rating " +
            "where rating = '2' " +
            "group by bookid) as B on B.bookid = bu.id " +
            "left join (select bookid,count(*) as cc from book2rating " +
            "where rating = '3'  " +
            "group by bookid) as C on C.bookid = bu.id " +
            "left join (select bookid,count(*) as cc from book2rating " +
            "where rating = '4'" +
            "group by bookid) as D on D.bookid = bu.id " +
            "left join (select bookid,count(*) as cc from book2rating " +
            "where rating = '5'" +
            "group by bookid) as E on E.bookid = bu.id " +
            "where (A.cc is not null or B.cc is not null or " +
            "C.cc is not null or " +
            "D.cc is not null or " +
            "E.cc is not null) and bu.id = ?1 " +
            "order by ratings desc", nativeQuery = true)
     Optional<BookRatingStars> findBookRatingByBookId(int id);

    //Расчет книг отсортированные по популярности книги и по дате добавляения
    @Query(value = "select bu.id as book_id,bu.title, COALESCE(A.cc, 0) as one, COALESCE(B.cc, 0) as two," +
            "            COALESCE(C.cc, 0) as three,COALESCE(D.cc, 0) as four," +
            "            COALESCE(E.cc, 0) as five, COALESCE((3*COALESCE(C.cc, 0)+ 2*COALESCE(B.cc, 0)+ COALESCE(A.cc, 0) +" +
            "             4*COALESCE(D.cc, 0)+5*COALESCE(E.cc, 0))/" +
            " NULLIF(" +
            "             (COALESCE(C.cc, 0)+ COALESCE(B.cc, 0)+ COALESCE(A.cc, 0) +" +
            "             COALESCE(D.cc, 0)+COALESCE(E.cc, 0)), 0),0) as ratings, pub_date " +
            "            from books as bu " +
            "            left join (select bookid,count(*) as cc from book2rating " +
            "            where rating = '1' " +
            "            group by bookid) as A on A.bookid = bu.id " +
            "            left join (select bookid,count(*) as cc from book2rating " +
            "            where rating = '2' " +
            "            group by bookid) as B on B.bookid = bu.id " +
            "            left join (select bookid,count(*) as cc from book2rating " +
            "            where rating = '3'  " +
            "            group by bookid) as C on C.bookid = bu.id " +
            "            left join (select bookid,count(*) as cc from book2rating " +
            "            where rating = '4'" +
            "            group by bookid) as D on D.bookid = bu.id " +
            "            left join (select bookid,count(*) as cc from book2rating " +
            "            where rating = '5' " +
            "            group by bookid) as E on E.bookid = bu.id " +
            "            order by ratings desc, pub_date desc" , nativeQuery = true)
    Page<BookRatingStars> findBooksRatingDesc(Pageable page);

}
