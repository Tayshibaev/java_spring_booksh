package com.example.MyBookShopApp.data.book.links;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;

import javax.persistence.*;

@Entity
@Table(name = "book2genre")
public class Book2GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "book_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    @ManyToOne
    private Book bookId;

    @JoinColumn(name = "genre_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    @ManyToOne
    private GenreEntity genreId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public GenreEntity getGenreId() {
        return genreId;
    }

    public void setGenreId(GenreEntity genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        return "Book2GenreEntity{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", genreId=" + genreId +
                '}';
    }
}
