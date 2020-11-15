package br.com.btsoftware.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.filter.DailySalesFilter;
import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.model.dto.DailySales;
import br.com.btsoftware.algafood.domain.model.enumerable.RequestStatus;
import br.com.btsoftware.algafood.domain.service.SaleQueryService;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<DailySales> findDailySales(DailySalesFilter filter, String timeOffset) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(DailySales.class);
		var root = query.from(Request.class);
		
		var functionConvertTzDateCreated = builder.function("timezone", Date.class, builder.literal(timeOffset), root.get("created"));
		
		var functionDateCreated = builder.function("TO_CHAR", String.class,
				functionConvertTzDateCreated, 
						builder.literal("yyyy-MM-dd"));
				
		
		var selection = builder.construct(DailySales.class,
				functionDateCreated,
				builder.count(root.get("id")),
				builder.sum(root.get("valueTotal")));
		
		
		ArrayList<Predicate> predicates = new ArrayList<Predicate>();
		
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
		    
		predicates.add(root.get("requestStatus").in(
		        RequestStatus.CONFIRMED, RequestStatus.DELIVERED));

		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateCreated);
		
		return manager.createQuery(query).getResultList();
	}

}
