package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.RestaurantInput;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.Restaurant;

@Component
public class RestaurantInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		return modelMapper.map(restaurantInput, Restaurant.class);
	}
	
	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.com.btsoftware.algafood.domain.model.Cozinha was altered from 1 to 2
		restaurant.setKitchen(new Kitchen());
		
		modelMapper.map(restaurantInput, restaurant);
	}
}
