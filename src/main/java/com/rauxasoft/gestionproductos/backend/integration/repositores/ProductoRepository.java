package com.rauxasoft.gestionproductos.backend.integration.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rauxasoft.gestionproductos.backend.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

	List<Producto> findByPrecioBetweenOrderByPrecioAsc(double min, double max);
	
}
