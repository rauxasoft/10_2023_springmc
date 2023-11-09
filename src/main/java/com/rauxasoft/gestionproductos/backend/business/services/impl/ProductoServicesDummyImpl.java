package com.rauxasoft.gestionproductos.backend.business.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rauxasoft.gestionproductos.backend.business.model.Familia;
import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;

@Service
public class ProductoServicesDummyImpl implements ProductoServices {
	
	private final TreeMap<Long, Producto> PRODUCTOS = new TreeMap<>();
	
	public ProductoServicesDummyImpl() {
		init();
	}
	
	@Override
	public Long create(Producto producto) {
		
		if(producto.getId() != null) {
			throw new IllegalStateException("No se puede crear un producto con código not null");
		}
		
		Long id = PRODUCTOS.lastKey() + 1;
		
		producto.setId(id);
		
		PRODUCTOS.put(producto.getId(), producto);
		
		return id;
	}

	@Override
	public Optional<Producto> read(Long id) {
		return Optional.ofNullable(PRODUCTOS.get(id));
	}
	
	@Override
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un producto con código not null");
		}
		
		boolean existe = PRODUCTOS.containsKey(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede actualizar.");
		}
		
		PRODUCTOS.replace(id, producto);
		
	}

	@Override
	public void delete(Long id) {
		
		boolean existe = PRODUCTOS.containsKey(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede eliminar.");
		}
		
		PRODUCTOS.remove(id);
	}
	
	@Override
	public List<Producto> getAll() {
		return new ArrayList<>(PRODUCTOS.values());
	}

	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		
		return PRODUCTOS.values().stream()
				.filter(x -> x.getPrecio() >= min && x.getPrecio() <= max)
				.collect(Collectors.toList());
	}

	// ***************************************************************
	//
	// Private Methods
	//
	// ***************************************************************

	private void init() {
		
		Producto p1 = new Producto();
		Producto p2 = new Producto();
		Producto p3 = new Producto();
		
		p1.setId(10L);
		p1.setNombre("Ordenador Epson D34");
		p1.setPrecio(150.0);
		p1.setDescripcion("Ordenador potente potente");
		p1.setDescatalogado(true);
		p1.setFamilia(Familia.HARDWARE);
		p1.setFechaAlta(new Date(100000000L));
		
		p2.setId(11L);
		p2.setNombre("Ordenador HP");
		p2.setPrecio(560.0);
		p2.setDescripcion("Ordenador potente potente");
		p2.setDescatalogado(false);
		p2.setFamilia(Familia.HARDWARE);
		p2.setFechaAlta(new Date(100000100L));
		
		p3.setId(12L);
		p3.setNombre("Impresora HP 2P Plus");
		p3.setPrecio(90.0);
		p3.setDescripcion("Impresora potente potente");
		p3.setDescatalogado(true);
		p3.setFamilia(Familia.HARDWARE);
		p3.setFechaAlta(new Date(100000600L));
		
		PRODUCTOS.put(p1.getId(), p1);
		PRODUCTOS.put(p2.getId(), p2);
		PRODUCTOS.put(p3.getId(), p3);
		
	}

}