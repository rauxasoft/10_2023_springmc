package com.rauxasoft.gestionproductos.backend.config;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {

	@Bean
	public DozerBeanMapper mapper() {
		
		DozerBeanMapper mapper = new DozerBeanMapper();

		List<String> mappingFiles = List.of("dozer-configuration-mapping.xml");
		
		mapper.setMappingFiles(mappingFiles);
		
		return mapper;
	}
	
}
