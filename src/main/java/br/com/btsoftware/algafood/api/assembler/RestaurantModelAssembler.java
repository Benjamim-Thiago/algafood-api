package br.com.btsoftware.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.KitchenModel;
import br.com.btsoftware.algafood.api.model.RestaurantModel;
import br.com.btsoftware.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {
	
	public RestaurantModel toModel(Restaurant restaurant) {
		KitchenModel kitchenModel = new KitchenModel();
		kitchenModel.setId(restaurant.getKitchen().getId());
		kitchenModel.setName(restaurant.getKitchen().getName());
		
		RestaurantModel restaurantModel = new RestaurantModel();
		restaurantModel.setId(restaurant.getId());
		restaurantModel.setName(restaurant.getName());
		restaurantModel.setDeliveryFee(restaurant.getDeliveryFee());
		restaurantModel.setKitchen(kitchenModel);
		return restaurantModel;
	}
	
	public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(restaurant -> toModel(restaurant))
				.collect(Collectors.toList());
	}
}
