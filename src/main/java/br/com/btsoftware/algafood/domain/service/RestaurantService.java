package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityNotFoundExeception;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.KitchenRepository;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenRepository kitchenRepository;
	
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen =  kitchenRepository.findById(kitchenId)
				.orElseThrow(() -> new EntityNotFoundExeception(
						String.format("Não existe um cadastro de cozinha com código %d",  kitchenId)));
		
		restaurant.setKitchen(kitchen);
		
		return restaurantRepository.save(restaurant);
	}
}
