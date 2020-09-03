package br.com.btsoftware.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long>{
	
}
