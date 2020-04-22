package br.com.btsoftware.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;

@Component
public class RestaurantEmpl implements RestaurantRepository {
	@PersistenceContext
	private EntityManager manager;

	public List<Restaurant> list() {
		return manager.createQuery("from Restaurant", Restaurant.class).getResultList();
	}

	public Restaurant find(Long id) {
		return manager.find(Restaurant.class, id);
	}

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		return manager.merge(restaurant);
	}

	@Transactional
	public void delete(Restaurant restaurant) {
		restaurant = find(restaurant.getId());
		manager.remove(restaurant);

	}
}
