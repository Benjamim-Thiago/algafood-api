package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;
import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.model.State;
import br.com.btsoftware.algafood.domain.repository.CityRepository;

@Service
public class CityService {
	private static final String CITY_IN_USE_MESSAGE = "Cidade com código %d não pode ser removida, pois está em uso";

	private static final String CITY_NOT_FOUND_MESSAGE = "Não existe um cadastro da Cidade com código %d";

	@Autowired
	private StateService stateService;
	
	@Autowired
	private CityRepository cityRepository;

	public City save(City city) {
		Long stateId = city.getState().getId();
		State state = stateService.findOrFail(stateId);
		city.setState(state);

		return cityRepository.save(city);
	}

	public void remove(Long id) {
		try {
			cityRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotExistException(String.format(CITY_NOT_FOUND_MESSAGE, id));

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(CITY_IN_USE_MESSAGE, id));
		}
	}
	
	public City findOrFail(Long id) {
		return cityRepository.findById(id).orElseThrow(
					() -> new EntityNotExistException(
							String.format(CITY_NOT_FOUND_MESSAGE, id)
						)
				);
	}
}
