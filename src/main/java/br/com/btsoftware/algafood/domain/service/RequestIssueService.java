package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.model.PaymentMode;
import br.com.btsoftware.algafood.domain.model.Product;
import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.model.User;
import br.com.btsoftware.algafood.domain.repository.RequestRepository;

/**
 * Emissão de Pedido 
 */
@Service
public class RequestIssueService {

	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private CityService cityService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PaymentModeService paymentModeService;
	
	@Transactional
	public Request save(Request request) {
	    validateRequest(request);
	    validateItems(request);

	    request.setDeliveryFee(request.getRestaurant().getDeliveryFee());
	    request.calculatePriceTotal();

	    return requestRepository.save(request);
	}

	private void validateRequest(Request request) {
	    City city = cityService.findOrFail(request.getDeliveryAddress().getCity().getId());
	    User client = userService.findOrFail(request.getClient().getId());
	    Restaurant restaurant = restaurantService.findOrFail(request.getRestaurant().getId());
	    PaymentMode paymentMode = paymentModeService.findOrFail(request.getPaymentMode().getId());

	    request.getDeliveryAddress().setCity(city);
	    request.setClient(client);
	    request.setRestaurant(restaurant);
	    request.setPaymentMode(paymentMode);
	    
	    if (restaurant.doesNoAcceptPaymentMode(paymentMode)) {
	        throw new BusinessException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	        		paymentMode.getDescription()));
	    }
	}
	
	private void validateItems(Request request) {
	    request.getItems().forEach(item -> {
	        Product product = productService.findOrFail(
	                request.getRestaurant().getId(), item.getProduct().getId());
	        
	        item.setRequest(request);
	        item.setProduct(product);
	        item.setPrice(product.getPrice());
	    });
	}
	
}
