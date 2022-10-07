package com.example.MyBookShopApp.data;

import javax.persistence.*;

@Entity
public class BookPopular {

    @Id
    @Column(name = "book_id")
    private Integer bookId;//id книги
    private Integer acount;//отложена
    private Integer bcount;//в корзине
    private Integer ccount;//куплена
    private Integer dcount;//в архиве
    private Double rating;//ратинг популярности книги P = C + 0,7*B + 0,4*A

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

    public Integer getAcount() {
        return acount;
    }

    public void setAcount(Integer acount) {
        this.acount = acount;
    }

    public Integer getBcount() {
        return bcount;
    }

    public void setBcount(Integer bcount) {
        this.bcount = bcount;
    }

    public Integer getCcount() {
        return ccount;
    }

    public void setCcount(Integer ccount) {
        this.ccount = ccount;
    }

    public Integer getDcount() {
        return dcount;
    }

    public void setDcount(Integer dcount) {
        this.dcount = dcount;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "BookPopular{" +
                "bookId=" + bookId +
                ", acount=" + acount +
                ", bcount=" + bcount +
                ", ccount=" + ccount +
                ", dcount=" + dcount +
                ", rating=" + rating +
                ", book=" + book +
                '}';
    }
}
