package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.StateEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.State;
import br.com.btsoftware.algafood.domain.repository.StateRepository;

@Service
public class StateService {

	private static final String STATE_IN_USE_MESSAGE = "Estado com código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private StateRepository stateRepository;
	
	public State save(State state) {
		
		return stateRepository.save(state);
	}
	
	public void remove(Long id) {
		try {
			stateRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new StateEntityNotExistException(id);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(STATE_IN_USE_MESSAGE,  id));
		}
	}
	
	public State findOrFail(Long id) {
		return stateRepository.findById(id).orElseThrow(
				() -> new StateEntityNotExistException(id));
	}

}
