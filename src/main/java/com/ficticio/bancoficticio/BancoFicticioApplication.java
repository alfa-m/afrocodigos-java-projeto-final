package com.ficticio.bancoficticio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BancoFicticioApplication {
	@GetMapping("")
	public String home(){
		return "API do Banco Fictício";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BancoFicticioApplication.class, args);
	}

}
