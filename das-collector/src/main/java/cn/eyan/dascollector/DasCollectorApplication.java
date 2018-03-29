package cn.eyan.dascollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class DasCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasCollectorApplication.class, args);
	}
}
