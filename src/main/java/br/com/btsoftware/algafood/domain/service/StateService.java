package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotFoundExeception;
import br.com.btsoftware.algafood.domain.model.State;
import br.com.btsoftware.algafood.domain.repository.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;
	
	public State save(State state) {
		
		return stateRepository.save(state);
	}
	
	public void remove(Long id) {
		try {
			stateRepository.delete(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundExeception(
					String.format("Não existe um cadastro de Estado com código %d",  id));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("Estado com código %d não pode ser removida, pois está em uso",  id));
		}
	}

}
