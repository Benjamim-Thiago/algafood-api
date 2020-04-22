package br.com.btsoftware.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.repository.CityRepository;

@Component
public class CityRepositoryImpl implements CityRepository {
	@PersistenceContext
	private EntityManager manager;

	public List<City> list() {
		return manager.createQuery("from City", City.class).getResultList();
	}

	public City find(Long id) {
		return manager.find(City.class, id);
	}

	@Transactional
	public City save(City city) {
		return manager.merge(city);
	}

	@Transactional
	public void delete(City city) {
		city = find(city.getId());
		manager.remove(city);

	}
}
