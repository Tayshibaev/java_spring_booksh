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
    /*Заккоментировал, так как не знаю как сохранить запись при помощи save. Не дает из за ошибки duplicate key value violates unique constraint
    Вручную id из за аннотации GeneratedValue присвоить нельзя
    Автоматически он присваивает айди = 1
    При добавлении в бд записи, он видит, что такая уже есть с таким айди и не ошибку выкидывает
    Как это ошибку обойти я не знаю, нужна помощь
    Видимо из за того, что данные добавляем в data.sql такое происходит
    */
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL", name = "rating")
    private int rating;

    @JoinColumn(name = "bookid", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    @ManyToOne
    private Book bookId;

    @Override
    public String toString() {
        return "Book2RatingEntity{" +
                "id=" + id +
                ", rating=" + rating +
                ", bookId=" + bookId +
                '}';
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
