package br.com.btsoftware.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository
		extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

}
