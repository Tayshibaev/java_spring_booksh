package com.example.MyBookShopApp.data.book.links;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "book2user")
public class Book2UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "INT NOT NULL", name = "typeid")
    private int typeId;

    @JoinColumn(name = "bookid", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    @ManyToOne
    private Book bookId;

//    @Column(name = "bookid", columnDefinition = "INT NOT NULL")
//    private int bookId;

//    public Integer getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Integer bookId) {
//        this.bookId = bookId;
//    }

    @JoinColumn(name = "userid", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    @ManyToOne
    private UserEntity userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }
}
