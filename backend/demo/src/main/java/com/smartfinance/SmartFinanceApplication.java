package com.smartfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SmartFinanceApplication {

	public static void main(String[] args) {

		SpringApplication.run(SmartFinanceApplication.class, args);

	}

}
