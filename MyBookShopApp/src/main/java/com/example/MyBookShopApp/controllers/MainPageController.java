package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.services.BookAndTagsService;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.DTO.BooksPageDto;
import com.example.MyBookShopApp.DTO.SearchWordDto;
import com.example.MyBookShopApp.services.BooksRatingAndPopulatityService;
import com.example.MyBookShopApp.services.GenreAndBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final BooksRatingAndPopulatityService bookPopularService;
    private final BookAndTagsService bookAndTagsService;

    @Autowired
    private GenreAndBookService genreService;

    @Autowired
    public MainPageController(BookService bookService
            , BooksRatingAndPopulatityService bookPopularService
            , BookAndTagsService bookAndTagsService
    ) {
        this.bookService = bookService;
        this.bookPopularService = bookPopularService;
        this.bookAndTagsService = bookAndTagsService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getPageOfRecentBooks(0, 6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return bookPopularService.getRatingPopularBooks(0, 20);
    }

    @ModelAttribute("tags")
    public List<TagEntity> tags() {
        return bookAndTagsService.getTags();
    }

    @ModelAttribute("tagName")
    public TagEntity tag() {
        return new TagEntity();
    }

    @ModelAttribute("mainGenre")
    public List<GenreEntity> mainGenre() {
        return genreService.getAllGenres();
    }

    @ModelAttribute("genreBooks")
    public List<Book> genreBooks() {
        return new ArrayList<>();
    }

    @ModelAttribute("genreParents")
    public List<GenreEntity> genreParents() {
        return new ArrayList<>();
    }

    @ModelAttribute("genreSubject")
    public GenreEntity genreSubject() {
        return null;
    }

    @ModelAttribute("booksByTag")
    public List<Book> booksByTag() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/recent")
    public String recentPage() {
        return "books/recent";
    }

    @GetMapping("/popular")
    public String popularPage() {
        return "books/popular";
    }

    @GetMapping("/genres")
    public String genresPage() {
        return "genres/index";
    }

    @GetMapping("/tagPage/{tagName}")
    public String tagsPage(@PathVariable(value = "tagName") String id, Model model) {
        TagEntity tag = bookAndTagsService.getTagByName(id);
        List<Book> booksByTag = bookAndTagsService.getBooksByTagName(id, 0, 5).getContent();
        model.addAttribute("tagName", tag);
        model.addAttribute("booksByTag", booksByTag);
        return "tags/index";
    }

    @GetMapping("/genres/{slug}")
    public String genresSlugPage(@PathVariable("slug") String slug, Model model) {
        GenreEntity genre = genreService.genreEntityBySlug(slug);
        //  List<Book> books = genre.getBook2GenreEntity().stream().map((ent) -> ent.getBookId()).collect(Collectors.toList());

        List<GenreEntity> parentGenres = new ArrayList<>();
        GenreEntity parent = genre.getParentGenre();
        System.out.println("PARENT:" + parent);
        while (parent != null) {
            parentGenres.add(0, parent);
            parent = parent.getParentGenre();
            System.out.println("PARENT:" + parent);
        }
        parentGenres.forEach(System.out::println);
        List<Book> books = genreService.getBooksByGenreId(genre.getId(),0,5).getContent();
        model.addAttribute("genreBooks", books);
        model.addAttribute("genreParents", parentGenres);
        model.addAttribute("genreSubject", genre);

        return "genres/slug";
    }

//    @GetMapping("/books/recent")
//    @ResponseBody
//    public BooksPageDto getRecentBooksPage(@RequestParam("offset") Integer offset,
//                                           @RequestParam("limit") Integer limit) {
//        return new BooksPageDto(bookService.getPageOfRecentBooks(offset, limit).getContent());
//    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getFromToDateRecentBooksPage(@RequestParam(value = "from", required = false) String from,
                                                     @RequestParam(value = "to", required = false) String to,
                                                     @RequestParam("offset") Integer offset,
                                                     @RequestParam("limit") Integer limit) throws ParseException {
        return new BooksPageDto(bookService.getPageFromToDateOfRecentBooks(from != null ? new SimpleDateFormat("dd.MM.yyyy").parse(from) : null,
                to != null ? new SimpleDateFormat("dd.MM.yyyy").parse(to) : null,
                offset, limit).getContent());
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping("/books/tag/{id}")
    @ResponseBody
    public BooksPageDto tagsPage(@RequestParam("offset") Integer offset,
                                 @RequestParam("limit") Integer limit, @PathVariable("id") Long id) {
        List<Book> booksByTag = bookAndTagsService.getBooksByTagId(id,
                offset, limit).getContent();
        booksByTag.forEach(System.out::println);
        return new BooksPageDto(booksByTag);
    }

    @GetMapping("/books/genre/{id}")
    @ResponseBody
    public BooksPageDto genreElsePage(@RequestParam("offset") Integer offset,
                                 @RequestParam("limit") Integer limit, @PathVariable("id") Integer id) {
        List<Book> booksByTag =genreService.getBooksByGenreId(id,
                offset, limit).getContent();
        booksByTag.forEach(System.out::println);
        return new BooksPageDto(booksByTag);
    }


    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookPopularService.getRatingPopularBooks(offset, limit));
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) {
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResults",
                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
        return "/search/index";
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }
}
