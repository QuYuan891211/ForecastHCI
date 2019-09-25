package com.nmefc.forcasthci;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nmefc.forcasthci.dao")
public class ForcasthciApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForcasthciApplication.class, args);
	}

}
