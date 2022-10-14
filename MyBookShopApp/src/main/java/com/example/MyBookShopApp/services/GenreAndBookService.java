package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookPopular;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.repositories.BookRatingRepository;
import com.example.MyBookShopApp.repositories.BookRepository;
import com.example.MyBookShopApp.repositories.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenreAndBookService {

    private GenresRepository genresRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public GenreAndBookService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<GenreEntity> getAllGenres() {
        List<GenreEntity> genres = genresRepository.findAll().stream()
                .map((gg) -> {
                    if (gg.getParentId() == null) {
                        gg.setParentId(0);
                    }
                    return gg;
                }).sorted(Comparator.comparing(a -> -a.getBook2GenreEntity().size())).collect(Collectors.toList());
        List<GenreEntity> genres1 = new ArrayList<>();
        for (GenreEntity genre : genres) {
            Integer idParent = genre.getParentId();

            if (idParent != 0) {
                GenreEntity parent = genres.stream().filter(genreEntity -> genreEntity.getId() == idParent).findFirst().get();
                genre.setParentGenre(parent);
                if (parent.getChildEntities() == null) {
                    List<GenreEntity> geList = new ArrayList<>();
                    geList.add(genre);
                    parent.setChildEntities(geList);
                } else {
                    List<GenreEntity> gg = parent.getChildEntities();
                    gg.add(genre);
                    parent.setChildEntities(gg);
                }
            }
        }


        System.out.println("ВОТ ТУТ МАПА");
        genres.stream().filter(genre -> genre.getParentId() == null || genre.getParentId() == 0).forEach(System.out::println);
        return genres.stream().filter(genre -> genre.getParentId() == null || genre.getParentId() == 0)
                .collect(Collectors.toList());
    }


    public GenreEntity genreEntityBySlug(String slug) {
        return genresRepository.getGenreEntityBySlug(slug);
    }

    public Page<Book> getBooksByGenreId(Integer id, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getBookByGenreId(id, nextPage);
    }

}
