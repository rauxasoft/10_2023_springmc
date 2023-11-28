package com.rauxasoft.gestionproductos.backend.presentation.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringContextInfoController {

	private ApplicationContext applicationContext;
	
	@Autowired
	public SpringContextInfoController(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@GetMapping("/beans")
	public String[] getContext() {
		return applicationContext.getBeanDefinitionNames();	
	}
}
