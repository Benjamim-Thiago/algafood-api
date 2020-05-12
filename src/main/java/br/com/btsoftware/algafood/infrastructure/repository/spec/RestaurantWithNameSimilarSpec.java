package br.com.btsoftware.algafood.infrastructure.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.btsoftware.algafood.domain.model.Restaurant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestaurantWithNameSimilarSpec implements Specification<Restaurant> {

	private static final long serialVersionUID = 1L;
	
	private String name;

	@Override
	public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return builder.like(builder.lower(root.get("name")), 
							builder.lower(builder.literal("%" + name + "%")));
	}



}
