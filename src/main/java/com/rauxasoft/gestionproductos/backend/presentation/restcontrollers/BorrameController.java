package com.rauxasoft.gestionproductos.backend.presentation.restcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;
import com.rauxasoft.gestionproductos.backend.integration.repositores.ProductoRepository;

@RestController
@RequestMapping("/pruebas")
public class BorrameController {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private ProductoServices productoServices;
	
	@GetMapping("/estadistica2-business")
	public Map<Familia, Double> getEstadisticaBusiness2(){
		return productoServices.getEstadisticaPrecioMedioProductosPorFamilia();
	}
	
	@GetMapping("/estadistica1-business")
	public Map<Familia, Integer> getEstadisticaBusiness1(){
		return productoServices.getEstadisticaNumeroProductosPorFamilia();
	}
	
	@GetMapping("/estadistica1")
	public List<Object[]> getEstadistica1(){
		return productoRepository.getEstadisticaNumeroProductosByCategoria();
	}
	
	@GetMapping("/incrementar-precios")
	@Transactional
	public String incrementarPrecios(){
		
		double porcentaje = 50.0;
		
		Producto producto1 = new Producto();
		Producto producto2 = new Producto();
		
		producto1.setId(100L);
		producto2.setId(102L);
		
		List<Producto> productos = List.of(producto1, producto2);
		
		productoRepository.incrementarPrecios(productos, porcentaje);
		
		return "precios actualizados!";
	}
	
	
	@GetMapping("/filtrados-por-nombre/{texto}")
	public List<Producto> getFiltradosPorNombre(@PathVariable String texto){
		return productoRepository.findByNombroLikeIgnoreCase(texto);
	}
	
	@GetMapping("/descatalogados")
	public List<Producto> getDescatalogados(){
		return productoRepository.findByDescatalogadoTrue();
	}
	
	@GetMapping("/dto1")
	public Object getProducto1DTO() {
		return productoRepository.getAllProducto1DTO();
	}
	
}
