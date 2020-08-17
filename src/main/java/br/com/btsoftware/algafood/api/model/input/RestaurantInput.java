package br.com.btsoftware.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantInput {
	@NotBlank
	private String name;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal deliveryFee;
	
	@Valid
	@NotNull
	private KitchenIdInput kitchen;
}
