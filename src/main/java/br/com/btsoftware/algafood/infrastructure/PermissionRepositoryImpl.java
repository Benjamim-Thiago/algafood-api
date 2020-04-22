package br.com.btsoftware.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.Permission;
import br.com.btsoftware.algafood.domain.repository.PermissionRepository;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {
	@PersistenceContext
	private EntityManager manager;

	public List<Permission> list() {
		return manager.createQuery("from Permission", Permission.class).getResultList();
	}

	public Permission find(Long id) {
		return manager.find(Permission.class, id);
	}

	@Transactional
	public Permission save(Permission permission) {
		return manager.merge(permission);
	}

	@Transactional
	public void delete(Permission permission) {
		permission = find(permission.getId());
		manager.remove(permission);

	}
}
