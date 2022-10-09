package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagsRepository extends JpaRepository<TagEntity, Long> {
    TagEntity getTagEntityByName(String name);
    TagEntity getTagEntityById(Long id);
}
