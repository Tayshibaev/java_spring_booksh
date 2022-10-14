package com.example.MyBookShopApp.data.genre;

import com.example.MyBookShopApp.data.book.links.Book2GenreEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(columnDefinition = "INT")
    private Integer parentId;

    @Transient
    private GenreEntity parentGenre;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @OneToMany(mappedBy = "genreId")
    private List<Book2GenreEntity> book2GenreEntity;

    @Transient
    private List<GenreEntity> childEntities = new ArrayList<>();

    public List<Book2GenreEntity> getBook2GenreEntity() {
        return book2GenreEntity;
    }

    public void setBook2GenreEntity(List<Book2GenreEntity> book2GenreEntity) {
        this.book2GenreEntity = book2GenreEntity;
    }

    public List<GenreEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<GenreEntity> childEntities) {
        this.childEntities = childEntities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GenreEntity getParentGenre() {
        return parentGenre;
    }

    public void setParentGenre(GenreEntity parentGenre) {
        this.parentGenre = parentGenre;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenreEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", childEntities=" + childEntities +
                '}';
    }
}
