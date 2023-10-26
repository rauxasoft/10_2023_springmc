package com.rauxasoft.gestionproductos.backend.presentation.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;
import com.rauxasoft.gestionproductos.backend.presentation.config.PresentationException;

@RestController
public class ProductoController {

	@Autowired
	private ProductoServices productoServices;
	
	@GetMapping("/productos")
	public List<Producto> getAll(){
		return productoServices.getAll();
	}

	@GetMapping("/productos/{id}")
	public Producto read(@PathVariable Long id) {
		
		Optional<Producto> optional = productoServices.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No se encuentra el producto con código " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
}
