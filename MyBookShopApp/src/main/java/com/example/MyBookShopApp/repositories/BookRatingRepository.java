package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.BookPopular;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRatingRepository extends JpaRepository<
        BookPopular
        ,
        Integer> {

    @Query(value = "select bu.id as book_id, A.cc as acount, B.cc as bcount,C.cc as ccount,D.cc as dcount, (COALESCE(C.cc, 0)+0.7*COALESCE(B.cc, 0)+0.4*COALESCE(A.cc, 0)) as rating " +
            "from books as bu " +
            "left join (select bookid,count(*) as cc from book2user " +
            "where typeid = '1' " +
            "group by bookid) as A on A.bookid = bu.id " +
            "left join (select bookid,count(*) as cc from book2user " +
            "where typeid = '2' " +
            "group by bookid) as B on B.bookid = bu.id " +
            "left join (select bookid,count(*) as cc from book2user " +
            "where typeid = '3'  " +
            "group by bookid) as C on C.bookid = bu.id " +
            "left join (select bookid,count(*) as cc from book2user " +
            "where typeid = '4'  " +
            "group by bookid) as D on D.bookid = bu.id " +
            "order by rating desc", nativeQuery = true)
    Page<BookPopular> findBooksRatingDesc(Pageable pageable);
}
