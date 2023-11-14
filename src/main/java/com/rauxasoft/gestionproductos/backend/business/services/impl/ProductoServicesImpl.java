package com.rauxasoft.gestionproductos.backend.business.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;
import com.rauxasoft.gestionproductos.backend.integration.repositores.ProductoRepository;

@Service
public class ProductoServicesImpl implements ProductoServices{

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	@Transactional
	public Long create(Producto producto) {
		
		if(producto.getId() != null) {
			throw new IllegalStateException("No se puede crear un producto con código not null");
		}
		
		Long id = System.currentTimeMillis();
		producto.setId(id);
		
		productoRepository.save(producto);
		
		return id;
	}

	@Override
	public Optional<Producto> read(Long id) {	
		return productoRepository.findById(id);
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un producto con código not null");
		}
		
		boolean existe = productoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede actualizar.");
		}
		
		productoRepository.save(producto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoRepository.deleteById(id);
	}

	@Override
	public List<Producto> getAll() {
		return productoRepository.findAll();
	}

	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		return productoRepository.findByPrecioBetweenOrderByPrecioAsc(min, max);
	}

}
