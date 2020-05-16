package br.com.btsoftware.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.KitchenRepository;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private KitchenRepository kitchenRepository;

	@GetMapping("/restaurants/per-name-and-fee")
	public List<Restaurant> RestaurantsPerNameFee(String name, BigDecimal firstDeliveryFee,
			BigDecimal lastDeliveryFee) {

		return restaurantRepository.find(name, firstDeliveryFee, lastDeliveryFee);
	}

	@GetMapping("/restaurants/restaurants-with-free-delivery")
	public List<Restaurant> RestaurantswithFreeDelivery(String name) {

		return restaurantRepository.findWithFreeDelivery(name);
	}
	@GetMapping("/restaurants/first")
	public Optional<Restaurant> findFirstRestaurant() {

		return restaurantRepository.findFirstRegister();
	}
	@GetMapping("/kitchens/first")
	public Optional<Kitchen> findFirstKitchen() {

		return kitchenRepository.findFirstRegister();
	}
}
