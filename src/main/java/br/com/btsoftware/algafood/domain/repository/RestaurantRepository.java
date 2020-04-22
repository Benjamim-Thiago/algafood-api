package br.com.btsoftware.algafood.domain.repository;

import java.util.List;

import br.com.btsoftware.algafood.domain.model.Restaurant;

public interface RestaurantRepository {
	List<Restaurant> list();
	Restaurant find(Long id);
	Restaurant save(Restaurant restaurant);
	void delete(Restaurant restaurant);
}
