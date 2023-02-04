package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.DTO.SearchWordDto;
import com.example.MyBookShopApp.annotation.DurationTrackable;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BookshpCartController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    private final BookRepository bookRepository;

    @Autowired
    public BookshpCartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @DurationTrackable
    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFromCookieSlugs);
        }

        return "cart";
    }

    @DurationTrackable
    @GetMapping("/postponed")
    public String handlePostponedRequest(@CookieValue(value = "keptContents", required = false) String cartContents,
                                         Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isKeptEmpty", true);
        } else {
            model.addAttribute("isKeptEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookKept", booksFromCookieSlugs);
        }

        return "postponed";
    }

    @PostMapping("/changeBookStatus/UNLINK/kept/remove/{slug}")
    public String handleRemoveBookFromKeptRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "keptContents", required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("keptContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isKeptEmpty", false);
        } else {
            model.addAttribute("isKeptEmpty", true);
        }

        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/UNLINK/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }

        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/CART/{slug}")
    public String handleChangeBookCartStatus(@PathVariable("slug") String slug, @CookieValue(name = "cartContents",
            required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }

        return "redirect:/books/" + slug;
    }

    @PostMapping("/changeBookStatus/KEPT/{slug}")
    public String handleChangeBookKeptStatus(@PathVariable("slug") String slug, @CookieValue(name = "keptContents",
            required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("keptContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isKeptEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("keptContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isKeptEmpty", false);
        }

        return "redirect:/books/" + slug;
    }
}
