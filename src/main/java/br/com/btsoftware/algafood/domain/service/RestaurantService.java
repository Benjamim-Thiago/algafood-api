package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.RestaurantEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenService kitchenService;
	
	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen =  kitchenService.findOrFail(kitchenId);
		
		restaurant.setKitchen(kitchen);
		
		return restaurantRepository.save(restaurant);
	}
	
	@Transactional
	public void activate(Long id) {
		Restaurant restaurant = findOrFail(id);
		
		restaurant.activate();
	}
	
	@Transactional
	public void inactivate(Long id) {
		Restaurant restaurant = findOrFail(id);
		
		restaurant.inactivate();
	}
	
	public Restaurant findOrFail(Long restaurantId) {
		
		return restaurantRepository.findById(restaurantId).orElseThrow(
				() -> new RestaurantEntityNotExistException(restaurantId));
	}
}
