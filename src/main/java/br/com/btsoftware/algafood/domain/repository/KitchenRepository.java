package br.com.btsoftware.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algafood.domain.model.Kitchen;

public interface KitchenRepository extends JpaRepository<Kitchen, Long>{
	//List<Kitchen> searchPerName(String name);
}
