package com.api.kwanza_services;

import org.springframework.boot.SpringApplication;

public class TestKwanzaServicesApplication {

	public static void main(String[] args) {
		SpringApplication.from(KwanzaServicesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
