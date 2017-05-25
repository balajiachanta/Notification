package com.bala.main;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(value={"com.bala"})
@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages={"com.bala.drive.db.repo"})
@PropertySource(value="classpath:notification-local.properties")
public class NotificationApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext ctx =SpringApplication.run(NotificationApplication.class, args);
		//Arrays.asList(ctx.getBeanDefinitionNames()).forEach( n -> System.out.println(n));
		
	}
}
