package com.rauxasoft.gestionproductos.backend.business.services;

import java.util.List;
import java.util.Optional;

import com.rauxasoft.gestionproductos.backend.business.model.Producto;

public interface ProductoServices {

	Long create(Producto producto);	    // C
	Optional<Producto> read(Long id);   // R
	
	List<Producto> getAll();
	
}
