package com.example.demo;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;


@SpringBootApplication
@RestController
@RequestMapping("/")
public class apiAluraGeek {

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final CategoryRepository categoryRepository;

	apiAluraGeek(ProductRepository productRepository, CategoryRepository categoryRepository){
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@ResponseBody
	@GetMapping("/produto/{id}")
	public Optional<Product> buscarProduto(@PathVariable Long id){
		Optional<Product> product = productRepository.findById(id);

		return product;
	}

	@PostMapping("/produto")
	public void criarProduto(@RequestBody Product product){
		productRepository.save(product);

	}
	@PutMapping("/produto")
	public void atualizarProduto(@RequestBody Product product){
		productRepository.save(product);

	}
	@DeleteMapping("/{teste}")
	public void deletarProduto(@PathVariable String teste){
		// implementar
	}

	@PostMapping("/categoria")
	public void criarCategoria(@RequestBody Category category){
		categoryRepository.save(category);

	}

	public static void main(String[] args) {
		SpringApplication.run(apiAluraGeek.class, args);
	}

}

