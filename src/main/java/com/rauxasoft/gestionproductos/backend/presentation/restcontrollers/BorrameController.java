package com.rauxasoft.gestionproductos.backend.presentation.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rauxasoft.gestionproductos.backend.integration.repositores.ProductoRepository;

@RestController
public class BorrameController {

	@Autowired
	private ProductoRepository productoRepository;
	
	@GetMapping("/dto1")
	public Object getProducto1DTO() {
		return productoRepository.getAllProducto1DTO();
	}
}
