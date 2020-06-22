package br.com.btsoftware.algafood.api.controller;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.KitchenEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;
import br.com.btsoftware.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping
	public ResponseEntity<List<Restaurant>> list() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		// System.out.println("Acozinha do primeiro restaurante é:");
		// System.out.println(restaurants.get(0).getKitchen().getName());
		return ResponseEntity.ok(restaurants);
	}

	@GetMapping("/{id}")
	public Restaurant find(@PathVariable Long id) {
		return restaurantService.findOrFail(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant save(@RequestBody Restaurant restaurant) {
		try {			
			return restaurantService.save(restaurant);
		} catch (KitchenEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}

	@PutMapping("/{id}")
	public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
		try {
			Restaurant restaurantInDatabase = restaurantService.findOrFail(id);
			restaurant.setUpdated(LocalDate.now());
			BeanUtils.copyProperties(restaurant, restaurantInDatabase, "id", "paymentsMode", "address", "products",
					"created");
			
			return restaurantService.save(restaurantInDatabase);			
		} catch (KitchenEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

	@PatchMapping("/{id}")
	public Restaurant partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> filds) {

		Restaurant restaurantInDatabase = restaurantService.findOrFail(id);

		merge(filds, restaurantInDatabase);

		return update(id, restaurantInDatabase);

	}

	private void merge(Map<String, Object> oringinFilds, Restaurant destinyRestaurant) {
		// Objeto de mapeamento
		ObjectMapper objectMapper = new ObjectMapper();

		// Transforma os campos para os tipos existente na entidade Restaurant
		Restaurant originRestaurant = objectMapper.convertValue(oringinFilds, Restaurant.class);

		// Faz um loop atribuindo valores para objeto
		oringinFilds.forEach((propertyName, propertyValue) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
			field.setAccessible(true);

			Object newValue = ReflectionUtils.getField(field, originRestaurant);

			ReflectionUtils.setField(field, destinyRestaurant, newValue);
		});
	}

}
