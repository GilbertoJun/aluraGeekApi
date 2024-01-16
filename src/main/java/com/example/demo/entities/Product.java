package com.example.demo.entities;


import com.example.demo.repositories.CategoryRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@Entity
@Table(name="product")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    private Double price;

    private String imageUrl;

    @Transient
    private Long idCategory;


    public Long getId() {
        return id;
    }
    public Long getIdCategory() {
        return idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Integer getIdCategory() {
//        return idCategory;
//    }

//    public void setIdCategory(Category category) {this.idCategory = category.getId();}
//    public void setIdCategory(Integer idCategory) {this.idCategory = idCategory;}

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonProperty("idCategory")
    private void handleIdCategory(Long idCategory){
        this.idCategory = idCategory;
    }
}
