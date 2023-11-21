package com.rauxasoft.gestionproductos.backend.integration.repositories;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO;
import com.rauxasoft.gestionproductos.backend.integration.repositores.ProductoRepository;

@DataJpaTest
@Sql(scripts= {"/data/h2/schema_test.sql","/data/h2/data_test.sql"})
public class ProductoRepositoryTest {

	@Autowired
	private ProductoRepository productoRepository;
	
	private Producto producto1;
	private Producto producto2;
	private Producto producto3;
	private Producto producto4;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void obtenemos_productos_entre_rango_de_precios_en_orden_ascendente() {
		
		List<Producto> productos = productoRepository.findByPrecioBetweenOrderByPrecioAsc(20.0, 500.0);
		
		assertEquals(2, productos.size());
			
		assertTrue(producto4.equals(productos.get(0)));
		assertTrue(producto1.equals(productos.get(1)));
		
	}
	
	@Test
	void obtenermos_productos_descatalogados_por_familia() {
		
		List<Producto> productos = productoRepository.findDescatalogadosByFamilia(Familia.HARDWARE);
		
		assertEquals(1, productos.size());
		
		assertTrue(producto2.equals(productos.get(0)));
	}
	
	@Test
	void obtenemos_todos_los_Producto1DTO() {
		
		List<Producto1DTO> productos1DTO = productoRepository.getAllProducto1DTO();
		
		for(Producto1DTO producto1DTO: productos1DTO) {
			System.err.println(producto1DTO);
		}
	}
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private void initObjects() {
		
		producto1 = new Producto();
		producto2 = new Producto();
		producto3 = new Producto();
		producto4 = new Producto();
		
		producto1.setId(100L);
		producto2.setId(101L);
		producto3.setId(102L);
		producto4.setId(103L);
		
	}
	
}