package com.example.MyBookShopApp.data.book.links;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "book2rating")
public class Book2RatingEntity {

    public Book2RatingEntity() {
    }

    public Book2RatingEntity(int rating, Book bookId) {
        this.rating = rating;
        this.bookId = bookId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL", name = "rating")
    private int rating;

    @JoinColumn(name = "bookid", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    @ManyToOne
    private Book bookId;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
    private int userId;



    @Override
    public String toString() {
        return "Book2RatingEntity{" +
                "id=" + id +
                ", rating=" + rating +
                ", bookId=" + bookId +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book2RatingEntity that = (Book2RatingEntity) o;
        return id == that.id && rating == that.rating && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, bookId);
    }
}
