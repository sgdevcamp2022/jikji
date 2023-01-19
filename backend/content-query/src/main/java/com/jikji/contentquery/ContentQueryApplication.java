package com.jikji.contentquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ContentQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentQueryApplication.class, args);
	}

}
