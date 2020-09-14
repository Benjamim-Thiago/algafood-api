package br.com.btsoftware.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductModel {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Boolean active = Boolean.TRUE;
}
