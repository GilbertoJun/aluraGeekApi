package com.example.demo;

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

	@GetMapping("/teste")
	public void teste(){

		System.out.println("Chegamos na primeira rota");
	}
	@PostMapping("/teste")
	public void testePost(){
		System.out.println("Chegamos na primeira rota só POST");
	}

	public static void main(String[] args) {
		SpringApplication.run(apiAluraGeek.class, args);
	}

}
