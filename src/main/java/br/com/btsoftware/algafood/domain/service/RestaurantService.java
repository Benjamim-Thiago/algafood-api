package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private static final String RESTAURANT_NOT_FOUND_MESSAGE = "Não existe um cadastro de restaurante com código %d";

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenService kitchenService;
	
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen =  kitchenService.findOrFail(kitchenId);
		
		restaurant.setKitchen(kitchen);
		
		return restaurantRepository.save(restaurant);
	}
	
	public Restaurant findOrFail(Long restaurantId) {
		
		return restaurantRepository.findById(restaurantId).orElseThrow(
				() -> new EntityNotExistException(
						String.format(RESTAURANT_NOT_FOUND_MESSAGE,  restaurantId)
						)
				);
	}
}
