package com.rauxasoft.gestionproductos.backend.business.model.dtos;

import java.io.Serializable;

public class Producto1DTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private double precio;
	
	public Producto1DTO(String name, double precio) {
		this.name = name;
		this.precio = precio;
	}

	public String getName() {
		return name;
	}

	public double getPrecio() {
		return precio;
	}

	@Override
	public String toString() {
		return "Producto1DTO [name=" + name + ", precio=" + precio + "]";
	}
	

}
