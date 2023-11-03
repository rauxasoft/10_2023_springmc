package com.rauxasoft.gestionproductos.backend.presentation.restcontrollers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;
import com.rauxasoft.gestionproductos.backend.presentation.config.PresentationException;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private ProductoServices productoServices;
	
	// http://localhost:8080/productos
	// http://localhost:8080/productos?min=100&max=1000
	
	@GetMapping
	public List<Producto> getAll(@RequestParam(name = "min", required = false) Double min, 
								 @RequestParam(name = "max", required = false) Double max){
		
		List<Producto> productos = null;
		
		if(min != null && max != null) {
			productos = productoServices.getBetweenPriceRange(min, max);
		} else {
			productos = productoServices.getAll();
		}
			
		return productos;
	}
	
	// http://localhost:8080/productos/144523
	
	@GetMapping("/{id}")
	public Producto read(@PathVariable Long id) {
		
		Optional<Producto> optional = productoServices.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No se encuentra el producto con código " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Producto producto, UriComponentsBuilder ucb) {
		
		// A ver si el test lo detecta...
		
		Long codigo = productoServices.create(producto);
		
		URI uri = ucb.path("/productos/{codigo}").build(codigo);
		
		return ResponseEntity.created(uri).build();
	}
		
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		
		try {
			productoServices.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("No se encuentra el producto con código [" + id + "]. No se ha podido eliminar.", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Producto producto) {
		
		try {
			productoServices.update(producto);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}

}
