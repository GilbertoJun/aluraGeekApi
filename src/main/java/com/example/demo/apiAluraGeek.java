package com.example.demo;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

	@ResponseBody
	@PostMapping("/produto")
	public ResponseEntity<HashMap> criarProduto(@RequestBody Product product){
		HashMap<String, Object> response = new HashMap<String,Object>();


		if(product.getIdCategory() != null){
			Optional<Category> categoria = categoryRepository.findById(product.getIdCategory());
			if(categoria.isEmpty()){
				response.put("mensagem", "Categoria informada é invalida");
				return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
			}

			product.setCategory(categoria.get());
		} else {
			response.put("mensagem", "É necessário informar a categoria");
			return new ResponseEntity<HashMap>(response, HttpStatus.BAD_REQUEST);
		}


		productRepository.save(product);
		response.put("mensagem", "Cadastrado com sucesso");
		response.put("id", product.getId());
		return new ResponseEntity<HashMap>(response, HttpStatus.OK);

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

