package br.com.btsoftware.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantModel {
	private Long id;
	private String name;
	private BigDecimal deliveryFee;
	private KitchenModel kitchen;
}
