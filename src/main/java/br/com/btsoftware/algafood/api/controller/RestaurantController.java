package br.com.btsoftware.algafood.api.controller;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.btsoftware.algafood.domain.exception.EntityNotFoundExeception;
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
		return ResponseEntity.ok(restaurantRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> find(@PathVariable Long id) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);

		if (restaurant.isPresent()) {
			return ResponseEntity.ok(restaurant.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Restaurant restaurant) {
		try {
			restaurant = restaurantService.save(restaurant);

			return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
		try {

			Optional<Restaurant> restaurantInDatabase = restaurantRepository.findById(id);

			if (restaurantInDatabase.isPresent()) {
				restaurant.setUpdated(LocalDate.now());
				BeanUtils.copyProperties(restaurant, restaurantInDatabase.get(), "id", 
						"paymentsMode", "address", "created");

				restaurant = restaurantService.save(restaurantInDatabase.get());
				return ResponseEntity.ok(restaurant);

			}

			return ResponseEntity.notFound().build();

		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> filds) {

		Optional<Restaurant> restaurantInDatabase = restaurantRepository.findById(id);

		if (restaurantInDatabase.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		merge(filds, restaurantInDatabase.get());

		return update(id, restaurantInDatabase.get());

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
