package br.com.btsoftware.algafood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
}
