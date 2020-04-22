package br.com.btsoftware.algafood.domain.repository;

import java.util.List;

import br.com.btsoftware.algafood.domain.model.City;

public interface CityRepository {
	List<City> list();
	City find(Long id);
	City save(City city);
	void delete(City city);
}
