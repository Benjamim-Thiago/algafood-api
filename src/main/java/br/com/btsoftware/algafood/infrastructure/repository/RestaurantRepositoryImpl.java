package br.com.btsoftware.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurant> find(String name, BigDecimal firstDeliveryFee, BigDecimal lastDeliveryFee) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
		Root<Restaurant> root = criteria.from(Restaurant.class);

		var clausulePredicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(name)) {
			clausulePredicates.add(
					builder.like(builder.lower(root.get("name")), 
							     builder.lower(builder.literal("%" + name + "%"))));
		}

		if (firstDeliveryFee != null) {
			clausulePredicates.add(builder.greaterThanOrEqualTo(root.get("deliveryFee"), firstDeliveryFee));		
		}

		if (lastDeliveryFee != null) {
			clausulePredicates.add(builder.lessThanOrEqualTo(root.get("deliveryFee"), lastDeliveryFee));			
		}

		criteria.where(clausulePredicates.toArray(new Predicate[0]));

		TypedQuery<Restaurant> query = manager.createQuery(criteria);

		return query.getResultList();
	}

//	@Override
//	public List<Restaurant> find(String name, BigDecimal firstDeliveryFee, BigDecimal lastDeliveryFee) {
//		
//		var jpql = new StringBuilder();
//		jpql.append("from Restaurant where 0 = 0 ");
//		
//		var params = new HashMap<String, Object>();
//		
//		if (StringUtils.hasLength(name)) {
//			jpql.append("and LOWER(name) like :name ");
//			params.put("name", "%" + name.toLowerCase() + "%");
//		}
//		
//		if (firstDeliveryFee != null) {
//			jpql.append("and deliveryFee >= :firstFee ");
//			params.put("firstFee", firstDeliveryFee);
//		}
//		
//		if (lastDeliveryFee != null) {
//			jpql.append("and deliveryFee <= :lastFee ");
//			params.put("lastFee", lastDeliveryFee);
//		}
//		
//		TypedQuery<Restaurant> query = manager
//				.createQuery(jpql.toString(), Restaurant.class);
//		
//		params.forEach((chave, valor) -> query.setParameter(chave, valor));
//
//		return query.getResultList();
//	}

}
