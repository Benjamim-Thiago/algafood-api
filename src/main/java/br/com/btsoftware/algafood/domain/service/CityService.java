package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotFoundExeception;
import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.model.State;
import br.com.btsoftware.algafood.domain.repository.CityRepository;
import br.com.btsoftware.algafood.domain.repository.StateRepository;

@Service
public class CityService {
	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	public City save(City city) {
		Long stateId = city.getState().getId();
		State state = stateRepository.find(stateId);

		if (state == null) {
			throw new EntityNotFoundExeception(String.format("Não existe um estado com código %d", stateId));
		}

		city.setState(state);

		return cityRepository.save(city);
	}
	
	public void remove(Long id) {
		try {
			cityRepository.delete(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundExeception(
					String.format("Não existe um cadastro da Cidade com código %d",  id));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("Cidade com código %d não pode ser removida, pois está em uso",  id));
		}
	}
}
