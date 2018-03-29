package cn.eyan.dasregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DasRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasRegisterApplication.class, args);
	}
}
