package com.firstock.employee;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@SecurityScheme(name = "/javainuseapi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
