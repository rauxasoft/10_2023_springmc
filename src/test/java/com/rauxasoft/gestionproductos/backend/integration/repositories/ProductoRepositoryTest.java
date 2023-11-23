package com.rauxasoft.gestionproductos.backend.integration.repositories;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.rauxasoft.gestionproductos.backend.business.model.dtos.Producto1DTO;
import com.rauxasoft.gestionproductos.backend.integration.model.FamiliaPL;
import com.rauxasoft.gestionproductos.backend.integration.model.ProductoPL;
import com.rauxasoft.gestionproductos.backend.integration.repositores.ProductoPLRepository;

@DataJpaTest
@Sql(scripts= {"/data/h2/schema_test.sql","/data/h2/data_test.sql"})
public class ProductoRepositoryTest {

	@Autowired
	private ProductoPLRepository productoPLRepository;
	
	private ProductoPL producto1;
	private ProductoPL producto2;
	private ProductoPL producto3;
	private ProductoPL producto4;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void obtenemos_productos_entre_rango_de_precios_en_orden_ascendente() {
		
		List<ProductoPL> productosPL = productoPLRepository.findByPrecioBetweenOrderByPrecioAsc(20.0, 500.0);
		
		assertEquals(2, productosPL.size());
			
		assertTrue(producto4.equals(productosPL.get(0)));
		assertTrue(producto1.equals(productosPL.get(1)));
		
	}
	
	@Test
	void obtenermos_productos_descatalogados_por_familia() {
		
		List<ProductoPL> productosPL = productoPLRepository.findDescatalogadosByFamilia(FamiliaPL.HARDWARE);
		
		assertEquals(1, productosPL.size());
		
		assertTrue(producto2.equals(productosPL.get(0)));
	}
	
	@Test
	void obtenemos_todos_los_Producto1DTO() {
		
		List<Producto1DTO> productos1DTO = productoPLRepository.getAllProducto1DTO();
		
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
		
		producto1 = new ProductoPL();
		producto2 = new ProductoPL();
		producto3 = new ProductoPL();
		producto4 = new ProductoPL();
		
		producto1.setId(100L);
		producto2.setId(101L);
		producto3.setId(102L);
		producto4.setId(103L);
		
	}
	
}
