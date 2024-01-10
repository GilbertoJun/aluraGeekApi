package com.example.demo;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@SpringBootApplication
@RestController
@RequestMapping("/produto")
public class apiAluraGeek {

	@Autowired
	private final ProductRepository productRepository;

	apiAluraGeek(ProductRepository productRepository){
		this.productRepository = productRepository;
	}

	@ResponseBody
	@GetMapping("/{id}")
	public Optional<Product> teste(@PathVariable Long id){
		Optional<Product> product = productRepository.findById(id);

		return product;
	}
	@PostMapping("/teste")
	public void testePost(){
		System.out.println("Chegamos na primeira rota só POST");
	}

	@PostMapping()
	public void criarProduto(@RequestBody Product product){
		productRepository.save(product);

	}
	@PutMapping()
	public void atualizarProduto(@RequestBody Product product){
		productRepository.save(product);

	}
	@DeleteMapping("/{teste}")
	public void deletarProduto(@PathVariable String teste){
		// implementar
	}



	public static void main(String[] args) {
		SpringApplication.run(apiAluraGeek.class, args);
	}

}

