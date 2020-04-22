package br.com.btsoftware.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.State;
import br.com.btsoftware.algafood.domain.repository.StateRepository;

@Component
public class StateRepositoryImpl implements StateRepository {
	@PersistenceContext
	private EntityManager manager;

	public List<State> list() {
		return manager.createQuery("from State", State.class).getResultList();
	}

	public State find(Long id) {
		return manager.find(State.class, id);
	}

	@Transactional
	public State save(State state) {
		return manager.merge(state);
	}

	@Transactional
	public void delete(State state) {
		state = find(state.getId());
		manager.remove(state);

	}
}
