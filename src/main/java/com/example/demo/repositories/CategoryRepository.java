package com.example.demo.repositories;

import com.example.demo.entities.Category;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends Repository<Category, Long> {

    Category save(Category category);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    void deleteById(Long id);

    List<Category> findAllByProdutosNotNull();

}
