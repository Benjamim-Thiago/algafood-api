package br.com.btsoftware.algafood.domain.repository;

import br.com.btsoftware.algafood.domain.model.Kitchen;

public interface KitchenRepository extends CustomJpaRepository<Kitchen, Long>{
	//List<Kitchen> searchPerName(String name);
}
