package com.rauxasoft.gestionproductos.backend.business.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;

public interface ProductoServices {

	/**
	 * Devuelve el código que ha otorgado el sistema
	 * 
	 * Lanza una IllegalStateException si el código del producto no es null
	 * 
	 */
	Long create(Producto producto);	    // C
	
	Optional<Producto> read(Long id);   // R
	
	/**
	 * 
	 * Lanza una IllegalStateException si el código del producto es null o no existe en el sistema
	 * 
	 */
	void update(Producto producto);		// U
	
	/**
	 * Lanza una IllegalStateException si no existe la id en el sistema
	 * 
	 */
	void delete(Long id);				// D
	
	List<Producto> getAll();
	List<Producto> getBetweenPriceRange(double min, double max);
	List<Producto> getDescatalogados();
	List<Producto> getByNombreLikeIgnoreCase(String texto);
	
	/**
	 * 	Incrementa el precio de los productos un tanto por ciento...
	 * 
	 * 	Ejemplo: Si porncetaje = 2.0 -> precio = precio * 2.0 / 100
	 * 
	 */
	void incrementarPrecios(List<Producto> productos, double porcentaje);
	
	/**
	 * 	Incrementa el precio de los productos un tanto por ciento...
	 * 
	 * 	Ejemplo: Si porncetaje = 2.0 -> precio = precio * 2.0 / 100
	 * 
	 */
	void incrementarPrecios(Long[] codigosProducto, double porcentaje);
	
	Map<Familia, Integer> getEstadisticaNumeroProductosPorFamilia();
	
	Map<Familia, Double> getEstadisticaPrecioMedioProductosPorFamilia();
	
	
	
	
}
