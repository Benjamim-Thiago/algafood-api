package br.com.btsoftware.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.RestaurantModel;
import br.com.btsoftware.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestaurantModel toModel(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantModel.class);
	}
	
	public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(restaurant -> toModel(restaurant))
				.collect(Collectors.toList());
	}
}
