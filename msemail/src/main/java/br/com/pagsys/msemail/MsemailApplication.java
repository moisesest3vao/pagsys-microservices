package br.com.pagsys.msemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsemailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsemailApplication.class, args);
	}

}
