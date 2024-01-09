package com.example.demo;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@RequestMapping("/")
public class apiAluraGeek {

	@Autowired
	private final ProductRepository productRepository;

	apiAluraGeek(ProductRepository productRepository){
		this.productRepository = productRepository;
	}

	@GetMapping("/teste")
	public void teste(){

		System.out.println("Chegamos na primeira rota");
	}
	@PostMapping("/teste")
	public void testePost(){
		System.out.println("Chegamos na primeira rota só POST");
	}

	@PostMapping("/criar-produto")
	public void criarProduto(){

		Product product = new Product();
		product.setName("Produto XYZ");
		product.setCategory("Star Wars");
		product.setPrice(60.0);
		product.setImageUrl("");

		productRepository.save(product);

	}

	public static void main(String[] args) {
		SpringApplication.run(apiAluraGeek.class, args);
	}

}
