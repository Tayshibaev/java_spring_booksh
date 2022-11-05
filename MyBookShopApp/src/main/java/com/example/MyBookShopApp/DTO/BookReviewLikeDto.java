package com.example.MyBookShopApp.DTO;

public class BookReviewLikeDto {

    private String value;
    private String reviewid;

    public BookReviewLikeDto() {
    }

    public BookReviewLikeDto(String value, String reviewid) {
        this.value = value;
        this.reviewid = reviewid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }

    @Override
    public String toString() {
        return "BookReviewLikeDto{" +
                "value='" + value + '\'' +
                ", reviewid='" + reviewid + '\'' +
                '}';
    }
}
