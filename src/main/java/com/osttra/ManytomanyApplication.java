package com.osttra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManytomanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManytomanyApplication.class, args);
		
		System.setProperty("spring.datasource.username", "root");
		System.setProperty("spring.datasource.password", "admin");
		System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
		System.setProperty("spring.jpa.show-sql", "true");
		System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
		System.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/osttra_tema");
		
		
	}

}
