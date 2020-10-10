package br.com.btsoftware.algafood.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.btsoftware.algafood.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantModel {
	
	@JsonView({ RestaurantView.Resume.class, RestaurantView.OnlyIdAndName.class })
	private Long id;
	
	@JsonView({ RestaurantView.Resume.class, RestaurantView.OnlyIdAndName.class })
	private String name;
	
	@JsonView(RestaurantView.Resume.class)
	private BigDecimal deliveryFee;
	
	@JsonView(RestaurantView.Resume.class)
	private KitchenModel kitchen;
	
	private Boolean active = Boolean.TRUE; 
	private AddressModel address;
	private Boolean open;
}
