package br.com.btsoftware.algafood.api.model.mixin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.btsoftware.algafood.domain.model.Address;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.PaymentMode;
import br.com.btsoftware.algafood.domain.model.Product;

public abstract class RestaurantMixin {
	
	@JsonIgnoreProperties(value = "name", allowGetters = true)
	private Kitchen kitchen;
	
	@JsonIgnore
	private Address address;
	
	@JsonIgnore
	private List<PaymentMode> paymentsMode = new ArrayList<>();

	@JsonIgnore
	private List<Product> products = new ArrayList<>();

	//@JsonIgnore
	private LocalDate created;

	//@JsonIgnore
	private LocalDate updated;
}
