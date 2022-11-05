package com.example.MyBookShopApp.DTO;

public class BookReviewDto {

    private String bookId;
    private String text;

    public BookReviewDto() {
    }

    public BookReviewDto(String bookId, String text) {
        this.bookId = bookId;
        this.text = text;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
