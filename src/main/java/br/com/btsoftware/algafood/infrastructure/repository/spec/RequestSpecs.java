package br.com.btsoftware.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.repository.filter.RequestFilter;

public class RequestSpecs {

	public static Specification<Request> searchUseFilter(RequestFilter filter) {
		return (root, query, builder) ->{
			
			root.fetch("restaurant").fetch("kitchen");
			root.fetch("restaurant").fetch("address").fetch("city").fetch("state");
			root.fetch("client");
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getClientId() != null) {
				predicates.add(builder.equal(root.get("client"), filter.getClientId()));
			}
			
			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			if (filter.getFirstCreatedDate() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("created"), 
						filter.getFirstCreatedDate()));
			}
			
			if (filter.getLastCreatedDate() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("created"), 
						filter.getLastCreatedDate()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
		
}
