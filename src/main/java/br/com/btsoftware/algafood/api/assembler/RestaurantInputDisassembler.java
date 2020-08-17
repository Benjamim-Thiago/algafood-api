package br.com.btsoftware.algafood.api.assembler;

import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.RestaurantInput;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.Restaurant;

@Component
public class RestaurantInputDisassembler {
	
	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantInput.getName());
		restaurant.setDeliveryFee(restaurantInput.getDeliveryFee());
		
		Kitchen kitchen = new Kitchen();
		kitchen.setId(restaurantInput.getKitchen().getId());
		
		restaurant.setKitchen(kitchen);
		
		return restaurant;
	}
}
