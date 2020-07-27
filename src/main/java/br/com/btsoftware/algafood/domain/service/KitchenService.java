package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.KitchenEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenService {

	private static final String KITCHEN_IN_USE_MESSAGE = "Cozinha de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private KitchenRepository kitchenRepository;

	@Transactional
	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}

	@Transactional
	public void remove(Long id) {
		try {
			kitchenRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new KitchenEntityNotExistException(id);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(KITCHEN_IN_USE_MESSAGE,  id));
		}
	}
	
	public Kitchen findOrFail(Long kitchenId) {
		return kitchenRepository.findById(kitchenId)
				.orElseThrow(() -> new KitchenEntityNotExistException(kitchenId));
	}
}
