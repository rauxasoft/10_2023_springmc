package com.rauxasoft.gestionproductos.backend.presentation.restcontrollers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;

@WebMvcTest(controllers=ProductoController.class)
public class ProductoControllerTest {
	
	@Autowired
	private MockMvc miniPostman;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductoServices productoServices;
	
	private Producto producto1;
	private Producto producto2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void pedimos_todos_los_productos() throws Exception {
		
		// Arrange
		
		List<Producto> productos = Arrays.asList(producto1, producto2);
		when(productoServices.getAll()).thenReturn(productos);
		
		// Act
		
		MvcResult respuesta = miniPostman.perform(get("/productos").contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(productos);
		
		// System.err.println("RESPUESTA EN EL BODY: " + responseBody);
		// System.err.println("RESPUESTA ESPERADA  : " + respuestaEsperada);
		
		// Assert
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	// http://localhost:8080/productos?min=10&max=50
	
	@Test
	void pedimos_todos_los_productos_entre_rango_precios() throws Exception {
				
		List<Producto> productos = Arrays.asList(producto1, producto2);
		when(productoServices.getBetweenPriceRange(10, 500)).thenReturn(productos);
			
		MvcResult respuesta = miniPostman.perform(get("/productos").param("min", "10")
																   .param("max","500")
																   .contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(productos);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	@Test
	@Disabled
	void eliminamos_producto_ok() {
		fail("Not implemented yet!");
	}
	
	private void initObjects() {
		
		producto1 = new Producto();
		producto1.setId(100L);
		producto1.setNombre("Alfombrilla Mouse CR7");
		producto1.setDescatalogado(true);
		producto1.setPrecio(20.0);
		producto1.setFamilia(Familia.CONSUMIBLE);
		producto1.setFechaAlta(new Date(10000000000L));
		
		producto2 = new Producto();
		producto2.setId(101L);
		producto2.setNombre("Ordenador Epson D30");
		producto2.setDescatalogado(false);
		producto2.setPrecio(400.0);
		producto2.setFamilia(Familia.HARDWARE);
		producto2.setFechaAlta(new Date(10000000001L));
		
	}
	

}
