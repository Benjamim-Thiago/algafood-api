package br.com.btsoftware.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import br.com.btsoftware.algafood.domain.model.Restaurant;

public class RestaurantSpecs {

	public static Specification<Restaurant> withFreeDelivery() {
		return (root, query, builder) -> 
			builder.equal(root.get("deliveryFee"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurant> withNameSimilar(String name) {
		return (root, query, builder) ->
			builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
	}
	
}
