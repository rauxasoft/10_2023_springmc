package com.rauxasoft.gestionproductos.backend.business.services;

import java.util.List;

import com.rauxasoft.gestionproductos.backend.business.model.Producto;

public interface ProductoServices {

	Long create(Producto producto);	// C
	Producto read(Long id);			// R
	
	List<Producto> getAll();
	
}
