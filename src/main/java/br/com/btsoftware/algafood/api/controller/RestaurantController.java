package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		return ResponseEntity.ok(restaurantRepository.list());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> find(@PathVariable Long id) {
		Restaurant restaurant = restaurantRepository.find(id);

		if (restaurant != null) {
			return ResponseEntity.ok(restaurant);
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

			Restaurant restaurantInDatabase =  restaurantRepository.find(id);
			if (restaurantInDatabase != null) {
				BeanUtils.copyProperties(restaurant, restaurantInDatabase, "id");

				restaurant = restaurantService.save(restaurantInDatabase);
				return ResponseEntity.ok(restaurant);
				
			}
			
			return ResponseEntity.notFound().build();

		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
