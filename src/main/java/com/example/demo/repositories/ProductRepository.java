package com.example.demo.repositories;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, Long> {

        Product save(Product product);

        Optional<Product> findById(long id);
        List<Product> findAll();

        void deleteById(Long id);
    }

