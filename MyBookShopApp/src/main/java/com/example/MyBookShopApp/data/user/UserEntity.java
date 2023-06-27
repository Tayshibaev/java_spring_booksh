package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


//Основной класс по юзеру
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(name = "regtime")
    private Date regTime;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "userId")
    private List<Book2UserEntity> booksToUserEntity;

    @OneToMany(mappedBy = "userId")
    private List<BalanceTransactionEntity> balanceTransactions;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BookReviewEntity bookRatingStars;

//    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
//    private BookstoreUser userInfoAdditional;
//
//    public BookstoreUser getUserInfoAdditional() {
//        return userInfoAdditional;
//    }

//    public void setUserInfoAdditional(BookstoreUser userInfoAdditional) {
//        this.userInfoAdditional = userInfoAdditional;
//    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
