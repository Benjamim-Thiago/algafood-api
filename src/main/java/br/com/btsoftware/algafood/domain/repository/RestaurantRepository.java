package br.com.btsoftware.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository
		extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

	@Query("FROM Restaurant r JOIN FETCH r.kitchen LEFT JOIN FETCH r.paymentsMode")
	List<Restaurant> findAll();
}
