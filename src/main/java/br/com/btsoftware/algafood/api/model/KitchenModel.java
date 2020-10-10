package br.com.btsoftware.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.btsoftware.algafood.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KitchenModel {
	@JsonView(RestaurantView.Resume.class)
	private Long id;
	
	@JsonView(RestaurantView.Resume.class)
	private String name;
}
