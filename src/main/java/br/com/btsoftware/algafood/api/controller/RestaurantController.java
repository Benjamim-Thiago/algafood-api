package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.assembler.RestaurantModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.RestaurantInputDisassembler;
import br.com.btsoftware.algafood.api.model.RestaurantModel;
import br.com.btsoftware.algafood.api.model.input.RestaurantInput;
import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.CityEntityNotExistException;
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
	
	@Autowired
	private RestaurantModelAssembler restaurantAssembler;
	
	@Autowired
	private RestaurantInputDisassembler restaurantInputDisassembler;
	
	@GetMapping
	public List<RestaurantModel> list() {
		return restaurantAssembler.toCollectionModel(restaurantRepository.findAll());
	}

	@GetMapping("/{id}")
	public RestaurantModel find(@PathVariable Long id) {
		Restaurant restaurant = restaurantService.findOrFail(id);
		return restaurantAssembler.toModel(restaurant);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantModel save(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {	
			
			Restaurant restaurant =  restaurantInputDisassembler.toDomainObject(restaurantInput);
			
			return restaurantAssembler.toModel(restaurantService.save(restaurant));
		} catch (KitchenEntityNotExistException | CityEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}

	@PutMapping("/{id}")
	public RestaurantModel update(@PathVariable Long id, @RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurantInDatabase = restaurantService.findOrFail(id);
			
			restaurantInputDisassembler.copyToDomainObject(restaurantInput, restaurantInDatabase);
						
			return restaurantAssembler.toModel(restaurantService.save(restaurantInDatabase));			
		} catch (KitchenEntityNotExistException | CityEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	@PutMapping("/{id}/activate")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long id) {
		restaurantService.activate(id);
	}
	
	@DeleteMapping("/{id}/activate")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivate(@PathVariable Long id) {
		restaurantService.inactivate(id);
	}


//	@PatchMapping("/{id}")
//	public Restaurant partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> filds, HttpServletRequest request) {
//
//		Restaurant restaurantInDatabase = restaurantService.findOrFail(id);
//
//		merge(filds, restaurantInDatabase, request);
//		
//		validate(restaurantInDatabase, "restaurant");
//		
//		return update(id, restaurantInDatabase);
//
//	}
//
//	private void merge(Map<String, Object> oringinFilds, Restaurant destinyRestaurant, HttpServletRequest request) {
		
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//			// Objeto de mapeamento
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//			
//			// Transforma os campos para os tipos existente na entidade Restaurant
//			Restaurant originRestaurant = objectMapper.convertValue(oringinFilds, Restaurant.class);
//	
//			// Faz um loop atribuindo valores para objeto
//			oringinFilds.forEach((propertyName, propertyValue) -> {
//				Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
//				field.setAccessible(true);
//	
//				Object newValue = ReflectionUtils.getField(field, originRestaurant);
//	
//				ReflectionUtils.setField(field, destinyRestaurant, newValue);
//			});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}
	
}
