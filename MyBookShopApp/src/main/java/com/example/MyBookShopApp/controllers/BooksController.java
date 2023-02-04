package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.DTO.SearchWordDto;
import com.example.MyBookShopApp.annotation.DurationTrackable;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRatingStars;
import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.data.book.links.Book2RatingEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.repositories.BookRepository;
import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import com.example.MyBookShopApp.services.BooksRatingStarsService;
import com.example.MyBookShopApp.services.BooksReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final ResourceStorage storage;
    private final BookstoreUserRegister userRegister;

    @Autowired
    private BooksRatingStarsService booksRatingStarsService;

    @Autowired
    private BooksReviewService booksReviewService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @Autowired
    public BooksController(BookRepository bookRepository, ResourceStorage storage, BookstoreUserRegister userRegister) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.userRegister = userRegister;
    }

    @DurationTrackable
    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model) {
        Book book = bookRepository.findBookBySlug(slug);
        BookRatingStars stars = booksRatingStarsService.getRatingPopularBooks(book.getId());
        Map<BookReviewEntity, Book2RatingEntity> reviewAndRating = booksReviewService.getBookReviewAndBook2RatingByBookId(book.getId());
        System.out.println("STARS: " + stars);
        model.addAttribute("slugBook", book);
        model.addAttribute("stars", stars);
        model.addAttribute("review", reviewAndRating);
        try {
            BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
            model.addAttribute("currUser", user);
            System.out.println("currUser=" + user);
        } catch (Exception e) {
            model.addAttribute("currUser", null);
            System.out.println("currUser is NULL");
        }
        return "/books/slug";
    }

    @PostMapping("/stars/{slug}/{value}")
    public String addStarsRating(@PathVariable("slug") String slug, @PathVariable("value") String value, Model model) {
        Book book = bookRepository.findBookBySlug(slug);
        System.out.println("SLUG AND VALUE: " + slug + "  " + value);
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        booksRatingStarsService.saveBook2Rating(book, user.getUserId().getId(), value);
        return "redirect:/books/" + slug;
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBoookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate); //save new path in db here

        return ("redirect:/books/" + slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }
}
