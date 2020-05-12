package br.com.btsoftware.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;
import br.com.btsoftware.algafood.infrastructure.repository.spec.RestaurantWithFreeDeliverySpec;
import br.com.btsoftware.algafood.infrastructure.repository.spec.RestaurantWithNameSimilarSpec;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private RestaurantRepository restaurantRepository;

	@GetMapping("/restaurants/per-name-and-fee")
	public List<Restaurant> RestaurantsPerNameFee(String name, BigDecimal firstDeliveryFee,
			BigDecimal lastDeliveryFee) {

		return restaurantRepository.find(name, firstDeliveryFee, lastDeliveryFee);
	}

	@GetMapping("/restaurants/restaurants-with-free-delivery")
	public List<Restaurant> RestaurantswithFreeDelivery(String name) {

		var withFreeDelivery = new RestaurantWithFreeDeliverySpec();
		var withNameSimilar = new RestaurantWithNameSimilarSpec(name);

		return restaurantRepository.findAll(withFreeDelivery.and(withNameSimilar));
	}
}
