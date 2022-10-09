package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.repositories.BookRepository;
import com.example.MyBookShopApp.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAndTagsService {

    private BookRepository bookRepository;
    private TagsRepository tagsRepository;

    @Autowired
    public BookAndTagsService(BookRepository bookRepository, TagsRepository tagsRepository) {
        this.bookRepository = bookRepository;
        this.tagsRepository = tagsRepository;
    }

    public List<TagEntity> getTags() {
        return tagsRepository.findAll();
    }

    public TagEntity getTagByName(String name) {
        return tagsRepository.getTagEntityByName(name);
    }

    public TagEntity getTagById(Long id) {
        return tagsRepository.getTagEntityById(id);
    }

    public Page<Book> getBooksByTagName(String name, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getBooksByTagName(name, nextPage);
    }

    public Page<Book> getBooksByTagId(Long id, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getBooksByTagId(id, nextPage);
    }
}
