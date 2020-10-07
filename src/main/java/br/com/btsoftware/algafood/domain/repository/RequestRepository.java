package br.com.btsoftware.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import br.com.btsoftware.algafood.domain.model.Request;

public interface RequestRepository extends CustomJpaRepository<Request, Long> {
	
	Optional<Request> findByCode(String code);
	
	
	@Query("from Request r join fetch r.client join fetch r.restaurant r join fetch r.kitchen join fetch r.address.city")
	List<Request> findAll();
	
}
