package com.rauxasoft.gestionproductos.backend.integration.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

	List<Producto> findByPrecioBetweenOrderByPrecioAsc(double min, double max);
	
	List<Producto> findByDescatalogadoTrue();
	
	List<Producto> findByDescatalogado(boolean descatalogado);
	
	@Query("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%',:text,'%'))")
	List<Producto> findByNombroLikeIgnoreCase(String text);
	
	@Query("UPDATE Producto p SET p.precio = p.precio + (p.precio * :porcentaje / 100) WHERE p IN :productos")
	@Modifying
	void incrementarPrecios(List<Producto> productos, double porcentaje);
	
	@Query("UPDATE Producto p SET p.precio = p.precio + (p.precio * :porcentaje / 100) WHERE p.id IN :ids")
	@Modifying
	void incrementarPrecios(Long[] ids, double porcentaje);
	
	@Query("SELECT p.familia, COUNT(p) FROM Producto p GROUP BY p.familia")
	List<Object[]> getEstadisticaNumeroProductosByCategoria();
	
	@Query("SELECT p.familia, AVG(p.precio) FROM Producto p GROUP BY p.familia")
	List<Object[]> getEstadisticaPrecioMedioProductosByCategoria();
	
	@Query("SELECT new com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO"
		   + "(CONCAT(p.nombre, ' - ', "
		   + "        p.familia), "
		   + "        p.precio) "
		   + "FROM Producto p")
	List<Producto1DTO> getAllProducto1DTO();
	
	
	@Query("SELECT p FROM Producto p WHERE p.descatalogado = true AND p.familia = :familia")
	List<Producto> findDescatalogadosByFamilia(Familia familia);
	
}
