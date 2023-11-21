package com.rauxasoft.gestionproductos.backend.integration.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

	List<Producto> findByPrecioBetweenOrderByPrecioAsc(double min, double max);
	
	@Query("SELECT p FROM Producto p WHERE p.descatalogado = true AND p.familia = :familia")
	List<Producto> findDescatalogadosByFamilia(Familia familia);
	
	@Query("SELECT new com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO"
		   + "(CONCAT(p.nombre, ' - ', "
		   + "        p.familia), "
		   + "        p.precio) "
		   + "FROM Producto p")
	List<Producto1DTO> getAllProducto1DTO();
	
	
}
