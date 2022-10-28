package com.example.MyBookShopApp.data;

import javax.persistence.*;

@Entity
public class BookRatingStars {

    public BookRatingStars() {
    }

    public BookRatingStars(Integer bookId) {
        this.bookId = bookId;
    }

    @Id
    @Column(name = "book_id")
    private Integer bookId;//id книги
    private Integer one=0;//отложена
    private Integer two=0;//в корзине
    private Integer three=0;//куплена
    private Integer four=0;//в архиве
    private Integer five=0;//в архиве

    public Integer getRatingRange() {
        return Math.round((float) (one+2*two+3*three+4*four+5*five)/(float) ((one+two+three+four+five)));
    }

    public Integer getSum() {
        return (one+two+three+four+five);
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "book_id")
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getOne() {
        return one;
    }

    public void setOne(Integer one) {
        this.one = one;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getFour() {
        return four;
    }

    public void setFour(Integer four) {
        this.four = four;
    }

    public Integer getFive() {
        return five;
    }

    public void setFive(Integer five) {
        this.five = five;
    }

    @Override
    public String toString() {
        return "BookRatingStars{" +
                "bookId=" + bookId +
                ", one=" + one +
                ", two=" + two +
                ", three=" + three +
                ", four=" + four +
                ", five=" + five +
                ", rating=" + getRatingRange() +
                ", book=" + book +
                '}';
    }
}
