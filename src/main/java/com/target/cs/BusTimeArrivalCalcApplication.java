package com.target.cs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.target.cs.*")
public class BusTimeArrivalCalcApplication {
	static final Logger LOGGER = LoggerFactory.getLogger(BusTimeArrivalCalcApplication.class);
	public static void main(String[] args) {

		SpringApplication.run(BusTimeArrivalCalcApplication.class, args);
		LOGGER.info("\n************************************************App Started************************************************************\n");
	}


	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

}
