package com.inventory.stock;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryStockApplication.class, args);
	}

	@Bean
	public OpenAPI custom(){
		return new OpenAPI()
				.info(new Info()
						.title("PRUEBA TECNICA SPRING")
						.version("1.0.0")
						.description("documentacion de la api para prueba tecnica		"));
	}

}
