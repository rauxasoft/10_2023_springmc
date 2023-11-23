package com.rauxasoft.gestionproductos.backend.integration.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO;
import com.rauxasoft.gestionproductos.backend.integration.model.FamiliaPL;
import com.rauxasoft.gestionproductos.backend.integration.model.ProductoPL;

public interface ProductoPLRepository extends JpaRepository<ProductoPL, Long>{

	List<ProductoPL> findByPrecioBetweenOrderByPrecioAsc(double min, double max);
	
	List<ProductoPL> findByDescatalogadoTrue();
	
	List<ProductoPL> findByDescatalogado(boolean descatalogado);
	
	@Query("SELECT p FROM ProductoPL p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%',:text,'%'))")
	List<ProductoPL> findByNombroLikeIgnoreCase(String text);
	
	@Query("UPDATE ProductoPL p SET p.precio = p.precio + (p.precio * :porcentaje / 100) WHERE p IN :productos")
	@Modifying
	void incrementarPrecios(List<ProductoPL> productos, double porcentaje);
	
	@Query("UPDATE ProductoPL p SET p.precio = p.precio + (p.precio * :porcentaje / 100) WHERE p.id IN :ids")
	@Modifying
	void incrementarPrecios(Long[] ids, double porcentaje);
	
	@Query("SELECT p.familia, COUNT(p) FROM ProductoPL p GROUP BY p.familia")
	List<Object[]> getEstadisticaNumeroProductosByCategoria();
	
	@Query("SELECT p.familia, AVG(p.precio) FROM ProductoPL p GROUP BY p.familia")
	List<Object[]> getEstadisticaPrecioMedioProductosByCategoria();
	
	@Query("SELECT new com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO"
		   + "(CONCAT(p.nombre, ' - ', "
		   + "        p.familia), "
		   + "        p.precio) "
		   + "FROM ProductoPL p")
	List<Producto1DTO> getAllProducto1DTO();
	
	
	@Query("SELECT p FROM ProductoPL p WHERE p.descatalogado = true AND p.familia = :familia")
	List<ProductoPL> findDescatalogadosByFamilia(FamiliaPL familia);
	
}
