package br.com.btsoftware.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.repository.KitchenRepository;

@Component
public class KitchenEmpl implements KitchenRepository {
	@PersistenceContext
	private EntityManager manager;

	public List<Kitchen> list() {
		return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
	}

	public Kitchen find(Long id) {
		return manager.find(Kitchen.class, id);
	}

	@Transactional
	public Kitchen save(Kitchen kitchen) {
		return manager.merge(kitchen);
	}

	@Transactional
	public void delete(Long id) {
		Kitchen kitchen = find(id);
		
		if(kitchen == null) {
			throw  new EmptyResultDataAccessException(1);
		}
		
		manager.remove(kitchen);

	}
}
