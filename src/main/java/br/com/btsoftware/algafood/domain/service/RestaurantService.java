package br.com.btsoftware.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.RestaurantEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.PaymentMode;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.model.User;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenService kitchenService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private PaymentModeService paymentModeService;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Long cityId = restaurant.getAddress().getCity().getId();
		
		Kitchen kitchen =  kitchenService.findOrFail(kitchenId);
		City city = cityService.findOrFail(cityId);
		
		restaurant.setKitchen(kitchen);
		restaurant.getAddress().setCity(city);
		
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
	
	@Transactional
	public void activate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::activate);
	}
	
	@Transactional
	public void inactivate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::inactivate);
	}
	
	@Transactional
	public void open(Long id) {
		//Metodo de Abertura do restaurant
		
		Restaurant restaurant = findOrFail(id);
		
		restaurant.open();
	}
	
	@Transactional
	public void close(Long id) {
		//Metodo de fechamento do restaurant
		
		Restaurant restaurant = findOrFail(id);
		
		restaurant.close();
	}
	
	public Restaurant findOrFail(Long restaurantId) {
		
		return restaurantRepository.findById(restaurantId).orElseThrow(
				() -> new RestaurantEntityNotExistException(restaurantId));
	}
	
	@Transactional
	public void desassociatePaymentMode(Long restaurantId, Long PaymentModeId) {
		Restaurant restaurant = findOrFail(restaurantId);
		PaymentMode paymentMode = paymentModeService.findOrFail(PaymentModeId);
		
		restaurant.removePaymentMode(paymentMode);
	}
	
	@Transactional
	public void associatePaymentMode(Long restaurantId, Long PaymentModeId) {
		Restaurant restaurant = findOrFail(restaurantId);
		PaymentMode paymentMode = paymentModeService.findOrFail(PaymentModeId);
		
		restaurant.addPaymentMode(paymentMode);
	}
	
	@Transactional
	public void disassociateCorporateOfficer(Long restaurantId, Long userId) {
	    Restaurant restaurant = findOrFail(restaurantId);
	    User user = userService.findOrFail(userId);
	    
	    restaurant.removeCorporateOfficer(user);
	}

	@Transactional
	public void associateCorporateOfficer(Long restaurantId, Long userId) {
	    Restaurant restaurant = findOrFail(restaurantId);
	    User user = userService.findOrFail(userId);
	    
	    restaurant.addCorporateOfficer(user);
	}
}
