package br.com.btsoftware.algafood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.model.Permission;

@Repository
public interface PermissionRepository extends CustomJpaRepository<Permission, Long>{
	Optional<Permission> findByName(String name);
}
