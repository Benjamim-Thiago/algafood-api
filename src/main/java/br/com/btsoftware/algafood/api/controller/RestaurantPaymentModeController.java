package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.assembler.PaymentModeModelAssembler;
import br.com.btsoftware.algafood.api.model.PaymentModeModel;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-mode")
public class RestaurantPaymentModeController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private PaymentModeModelAssembler paymentModeModelAssembler;
		
	@GetMapping
	public List<PaymentModeModel> list(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);
		
		return paymentModeModelAssembler.toCollectionModel(restaurant.getPaymentsMode());
	}
	
	@DeleteMapping("/{paymentModeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociate(@PathVariable Long restaurantId, @PathVariable Long paymentModeId) {
		restaurantService.desassociatePaymentMode(restaurantId, paymentModeId);
	}
	
	@PutMapping("/{paymentModeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restaurantId, @PathVariable Long paymentModeId) {
		restaurantService.associatePaymentMode(restaurantId, paymentModeId);
	}


}
