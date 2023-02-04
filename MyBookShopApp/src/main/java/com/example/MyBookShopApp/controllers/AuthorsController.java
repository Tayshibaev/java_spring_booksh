package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.DTO.BooksPageDto;
import com.example.MyBookShopApp.DTO.SearchWordDto;
import com.example.MyBookShopApp.annotation.DurationTrackable;
import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.services.AuthorService;
import com.example.MyBookShopApp.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    private BookService bookService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        return authorService.getAuthorsMap();
    }

    @DurationTrackable
    @GetMapping("/authors")
    public String authorsPage() {
        return "authors/index";
    }

    @ModelAttribute("authorBooks")
    public List<Book> authorBooks() {
        return new ArrayList<>();
    }

    @ApiOperation("method to get map of authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String, List<Author>> authors() {
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors/{slug}")
    public String authorSlugPage(@PathVariable("slug") String slug, Model model) {
        Author author = authorService.authorBySlug(slug);
        List<Book> books = bookService.getBooksByAuthorId(author.getId(), 0, 5).getContent();
        System.out.println("ТУТ КНИГИ");
        books.forEach(System.out::println);
        System.out.println("ТУТ АВТОР");
        System.out.println(author);
        model.addAttribute("authorBooks", books);
        model.addAttribute("author", author);
        return "authors/slug";
    }

    @GetMapping("/books/pageauthor/{id}")
    public String authorSlugPage(@PathVariable("id") Integer id, Model model) {
        Author author = authorService.authorById(id);
        List<Book> books =  bookService.getBooksByAuthorId(id, 0, 5).getContent();
        model.addAttribute("authorBooks", books);
        model.addAttribute("author", author);
        return "books/author";
    }

    @GetMapping("/books/author/{id}")
    @ResponseBody
    public BooksPageDto authorSlugPage(@RequestParam("offset") Integer offset,
                                       @RequestParam("limit") Integer limit, @PathVariable("id") Integer id) {
        Integer offset1 = offset<0?(-offset):offset;

        return new BooksPageDto(bookService.getBooksByAuthorId(id, offset1, limit).getContent());
    }

}
