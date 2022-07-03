package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class RecentPopularController {

    private BookService bookService;

    @Autowired
    public RecentPopularController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("booksList")
    public List<Book> getListBooks() {
        return bookService.getBooksData();
    }

    @GetMapping("/recent")
    public String recentPage(){
        return "books/recent";
    }

    @GetMapping("/popular")
    public String popularPage(){
        return "books/popular";
    }
}
