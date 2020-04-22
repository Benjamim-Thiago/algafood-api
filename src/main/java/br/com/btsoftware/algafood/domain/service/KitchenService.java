package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotFoundExeception;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenService {

	@Autowired
	private KitchenRepository kitchenRepository;

	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}

	public void remove(Long id) {
		try {
			kitchenRepository.delete(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundExeception(
					String.format("Não existe um cadastro de cozinha com código %d",  id));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso",  id));
		}
	}
}
