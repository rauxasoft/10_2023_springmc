package com.rauxasoft.gestionproductos.backend.business.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.rauxasoft.gestionproductos.backend.business.model.Producto;
import com.rauxasoft.gestionproductos.backend.business.services.ProductoServices;
import com.rauxasoft.gestionproductos.backend.integration.repositores.ProductoRepository;

@Service
@Primary
public class ProductoServicesImpl implements ProductoServices{

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Long create(Producto producto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Producto> read(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void update(Producto producto) {
		// TODO Auto-generated method stub	
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
		// TODO Auto-generated method stub
		return null;
	}

}
