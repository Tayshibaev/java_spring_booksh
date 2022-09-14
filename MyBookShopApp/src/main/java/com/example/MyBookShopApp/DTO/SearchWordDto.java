package com.example.MyBookShopApp.DTO;

public class SearchWordDto {

    private String example;

    public SearchWordDto(String example) {
        this.example = example;
    }

    public SearchWordDto(){}

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
