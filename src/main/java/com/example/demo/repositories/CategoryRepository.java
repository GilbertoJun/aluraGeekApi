package com.example.demo.repositories;

import com.example.demo.entities.Category;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CategoryRepository extends Repository<Category, Long> {

    Category save(Category category);

    Optional<Category> findById(long id);
}
