package br.com.btsoftware.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.btsoftware.algafood.domain.model.Restaurant;

public interface RestaurantRepositoryQueries {
	List<Restaurant> find(String name, BigDecimal firstDeliveryFee, BigDecimal lastDeliveryFee);
	
	List<Restaurant> findWithFreeDelivery(String name);

}
