package com.example.demo;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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
	@GetMapping("/produtos")
	public ResponseEntity<List> buscarProdutos(){

		List<Product> products = productRepository.findAll();

		return new ResponseEntity<List>(products, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/produto")
	public ResponseEntity<HashMap> criarProduto(@RequestBody Product product){
		HashMap<String, Object> response = new HashMap<String,Object>();


		if(product.getIdCategory() != null){
			Optional<Category> categoria = categoryRepository.findById((long)product.getIdCategory());
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
		Optional<Category> optionalCategory = categoryRepository.findById(product.getIdCategory());

		product.setCategory(optionalCategory.get());
		productRepository.save(product);
	}
	@DeleteMapping("/produto/{id}")
	public void deletarProduto(@PathVariable Long id){
		productRepository.deleteById(id);
	}

	@PostMapping("/categoria")
	public void criarCategoria(@RequestBody Category category){
		categoryRepository.save(category);

	}


	@GetMapping("/categoria/{id}")
	public Optional<Category> buscarCategoria(@PathVariable Long id){
		Optional<Category> category = categoryRepository.findById(id);

		return category;
	}

	@ResponseBody
	@GetMapping("/categorias")
	public ResponseEntity<List> buscarCategorias(@RequestParam(required = false) Boolean somenteComProduto, @RequestParam(required = false) String stringBusca){

        List<Category> categories;

        if(somenteComProduto != null && somenteComProduto){

            if(stringBusca == null) categories = categoryRepository.findAllByProdutosNotNull();// ONDE TEM PRODUTO
			else categories = categoryRepository.findAllByProdutosNotNullAndCategoryNameContaining(stringBusca);// ONDE TEM PRODUTO E DA MATCH COM STRING
        } else {

            if(stringBusca == null) categories = categoryRepository.findAll();// BUSCA TODAS
			else categories = categoryRepository.findAllByCategoryNameContaining(stringBusca);// BUSCA TODAS COM MATCH NA STRING
        }

        return new ResponseEntity<List>(categories, HttpStatus.OK);
	}


	@ResponseBody
	@DeleteMapping("/categoria/{id}")
	public ResponseEntity<HashMap> deletarCategoria(@PathVariable Long id){
		HashMap<String, Object> response = new HashMap<String, Object>();

		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if(optionalCategory.isEmpty()){
			response.put("mensagem", "Categoria não encontrada.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		if(productRepository.existsByCategory(optionalCategory.get())){
			response.put("mensagem", "Não é possível remover categorias com produtos vinculados.");
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}else{
			categoryRepository.deleteById(id);
			response.put("mensagem", "Removido com sucesso");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping("/categoria/{idCategory}/produtos/{pagina}")
	public ResponseEntity<HashMap> buscarProdutosDeCategoria(@PathVariable Long idCategory, @PathVariable(required = false) Integer pagina, @RequestParam(required = false) String stringBusca){
		HashMap<String, Object> response = new HashMap<String, Object>();

		Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
		if(optionalCategory.isEmpty()){
			response.put("mensagem", "Categoria não encontrada.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		Pageable pageable = PageRequest.of(pagina - 1, 10);
		Page<Product> paginaDeProdutos;

		if(stringBusca == null) 	paginaDeProdutos = productRepository.findByCategory(optionalCategory.get(), pageable);
		else 						paginaDeProdutos = productRepository.findByCategoryAndNameContaining(optionalCategory.get(), stringBusca, pageable);

		List listaDeProdutos = paginaDeProdutos.getContent();

		response.put("produtos", listaDeProdutos);


		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	}


	public static void main(String[] args) {
		SpringApplication.run(apiAluraGeek.class, args);
	}

}

