package br.com.pagsys.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(r -> r
						.path("/payment/**")
					//	.filters(f -> f.addRequestHeader("username",""))
						.uri("lb://mspayment"))
				.route(r -> r
						.path("/users/**")
						//	.filters(f -> f.addRequestHeader("username",""))
						.uri("lb://msusers"))
				.build();
	}

}
