package com.rauxasoft.gestionproductos.backend.business.services.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rauxasoft.gestionproductos.backend.integration.model.ProductoPL;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;
import com.rauxasoft.gestionproductos.backend.integration.repositores.ProductoPLRepository;

@Service
public class ProductoServicesImpl implements ProductoServices{

	@Autowired
	private ProductoPLRepository productoPLRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	@Transactional
	public Long create(Producto producto) {
		
		if(producto.getId() != null) {
			throw new IllegalStateException("No se puede crear un producto con código not null");
		}
		
		Long id = System.currentTimeMillis();
		producto.setId(id);
		
		ProductoPL productoPL = mapper.map(producto, ProductoPL.class);
		
		productoPLRepository.save(productoPL);
		
		return id;
	}

	@Override
	public Optional<Producto> read(Long id) {	
		
		Optional<ProductoPL> optional = productoPLRepository.findById(id);
		
		Producto producto = null;
		
		if(optional.isPresent()) {
			producto = mapper.map(optional.get(), Producto.class);
		}
		
		return Optional.ofNullable(producto);
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un producto con código not null");
		}
		
		boolean existe = productoPLRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede actualizar.");
		}
		
		ProductoPL productoPL = mapper.map(producto, ProductoPL.class);
		
		productoPLRepository.save(productoPL);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoPLRepository.deleteById(id);
	}

	@Override
	public List<Producto> getAll() {
		return convert(productoPLRepository.findAll());
	}

	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		return convert(productoPLRepository.findByPrecioBetweenOrderByPrecioAsc(min, max));
	}

	@Override
	public List<Producto> getDescatalogados() {
		return convert(productoPLRepository.findByDescatalogadoTrue());
	}

	@Override
	public List<Producto> getByNombreLikeIgnoreCase(String texto) {
		return convert(productoPLRepository.findByNombroLikeIgnoreCase(texto));
	}

	@Override
	@Transactional
	public void incrementarPrecios(List<Producto> productos, double porcentaje) {
		
		List<ProductoPL> productosPL = productos.stream()
										.map(x -> mapper.map(x, ProductoPL.class))
										.collect(Collectors.toList());
		
		productoPLRepository.incrementarPrecios(productosPL, porcentaje);
		
	}

	@Override
	@Transactional
	public void incrementarPrecios(Long[] idsProducto, double porcentaje) {
		productoPLRepository.incrementarPrecios(idsProducto, porcentaje);
	}

	@Override
	public Map<Familia, Integer> getEstadisticaNumeroProductosPorFamilia() {
		
		List<Object[]> resultSet = productoPLRepository.getEstadisticaNumeroProductosByCategoria();
		
		Map<Familia, Integer> estadistica = new HashMap<>();
		
		for(Object[] fila: resultSet) {
			Familia familia = (Familia) fila[0];
			Integer numeroProductos = ((Long) fila[1]).intValue();
			estadistica.put(familia, numeroProductos);
		}
		
		return estadistica;
	}

	@Override
	public Map<Familia, Double> getEstadisticaPrecioMedioProductosPorFamilia() {
		
		List<Object[]> resultSet = productoPLRepository.getEstadisticaPrecioMedioProductosByCategoria();
		
		Map<Familia, Double> estadistica = new HashMap<>();
		
		for(Object[] fila: resultSet) {
			Familia familia = (Familia) fila[0];
			Double numeroProductos = (Double) fila[1];
			estadistica.put(familia, numeroProductos);
		}
		
		return estadistica;
	}
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private List<Producto> convert(List<ProductoPL> productosPL){
		
		return productosPL.stream()
				.map(x -> mapper.map(x, Producto.class))
				.collect(Collectors.toList());
	}

}
