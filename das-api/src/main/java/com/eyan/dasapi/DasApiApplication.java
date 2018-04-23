package com.eyan.dasapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages={"com.eyan","cn.hycun"})
public class DasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasApiApplication.class, args);
	}
}
