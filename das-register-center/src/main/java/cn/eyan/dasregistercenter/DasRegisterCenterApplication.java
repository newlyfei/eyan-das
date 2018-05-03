package cn.eyan.dasregistercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DasRegisterCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasRegisterCenterApplication.class, args);
	}
}
