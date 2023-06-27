package com.example.MyBookShopApp.data.payments;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_transaction")
public class BalanceTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    @ManyToOne
    private UserEntity userId;//идентификатор пользователя

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;//дата и время транзакции

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int value;//размер транзакции (положительный — зачисление, отрицательный — списание)

    @JoinColumn(name = "book_id", referencedColumnName = "id", columnDefinition = "INT")
    @ManyToOne
    private Book bookId;// книга, за покупку которой произошло списание, или NULL

    @Column(columnDefinition = "TEXT NOT NULL")
    private String description;//описание транзакции: если зачисление, то откуда, если списание, то на что

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
