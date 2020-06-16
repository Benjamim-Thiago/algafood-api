package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenService {

	private static final String KITCHEN_IN_USE_MESSAGE = "Cozinha de código %d não pode ser removida, pois está em uso";
	private static final String KITCHEN_NOT_FOUND_MESSAGE = "Não existe um cadastro de cozinha com código %d";
	@Autowired
	private KitchenRepository kitchenRepository;

	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}

	public void remove(Long id) {
		try {
			kitchenRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotExistException(
					String.format(KITCHEN_NOT_FOUND_MESSAGE,  id));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(KITCHEN_IN_USE_MESSAGE,  id));
		}
	}
	
	public Kitchen findOrFail(Long kitchenId) {
		return kitchenRepository.findById(kitchenId)
				.orElseThrow(() -> new EntityNotExistException(
						String.format(KITCHEN_NOT_FOUND_MESSAGE, kitchenId))
				);
	}
}
