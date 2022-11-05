package com.example.MyBookShopApp.data.book.review;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "book_review")
public class BookReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

//    @Column(columnDefinition = "INT NOT NULL")
//    private int userId;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "reviewId")
    private List<BookReviewLikeEntity> bookReviewLikeEntity;

    public List<BookReviewLikeEntity> getBookReviewLikeEntity() {
        return bookReviewLikeEntity;
    }

    public void setBookReviewLikeEntity(List<BookReviewLikeEntity> bookReviewLikeEntity) {
        this.bookReviewLikeEntity = bookReviewLikeEntity;
    }

    public Long getLikes() {
        return bookReviewLikeEntity.stream().filter(x->x.getValue()==1).count();
    }

    public Long getDisLikes() {
        return bookReviewLikeEntity.stream().filter(x->x.getValue()==-1).count();
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getFormattedTime() {
        return time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
