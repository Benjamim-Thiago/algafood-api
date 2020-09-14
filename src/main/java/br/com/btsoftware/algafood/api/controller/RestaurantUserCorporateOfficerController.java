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

import br.com.btsoftware.algafood.api.assembler.UserModelAssembler;
import br.com.btsoftware.algafood.api.model.UserModel;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/officers")
public class RestaurantUserCorporateOfficerController {
	 @Autowired
	    private RestaurantService restaurantService;
	    
	    @Autowired
	    private UserModelAssembler userModelAssembler;
	    
	    @GetMapping
	    public List<UserModel> list(@PathVariable Long restaurantId) {
	        Restaurant restaurant = restaurantService.findOrFail(restaurantId);
	        
	        return userModelAssembler.toCollectionModel(restaurant.getCorporate_officers());
	    }
	    
	    @DeleteMapping("/{userId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void desassociate(@PathVariable Long restaurantId, @PathVariable Long userId) {
	        restaurantService.disassociateCorporateOfficer(restaurantId, userId);
	    }
	    
	    @PutMapping("/{userId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void associate(@PathVariable Long restaurantId, @PathVariable Long userId) {
	        restaurantService.associateCorporateOfficer(restaurantId, userId);
	    }
}
