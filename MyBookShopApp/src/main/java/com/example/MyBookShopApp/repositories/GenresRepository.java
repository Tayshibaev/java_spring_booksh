package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenresRepository extends JpaRepository<GenreEntity, Long> {
    GenreEntity getGenreEntityBySlug(String slug);
}
