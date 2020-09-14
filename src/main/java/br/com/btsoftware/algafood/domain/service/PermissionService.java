package br.com.btsoftware.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.PermissionEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Permission;
import br.com.btsoftware.algafood.domain.repository.PermissionRepository;

@Service
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Transactional
	public Permission save(Permission permission) {
		permissionRepository.detach(permission);
		
		Optional<Permission> permissionExist =  permissionRepository.findByName(permission.getName());
		
		if (permissionExist.isPresent() && !permissionExist.get().equals(permission)) {
			throw new BusinessException(
					String.format("Já existe uma permissão cadastrado com o nome %s", permission.getName()));	
		}
		
		return permissionRepository.save(permission);
	}

	@Transactional
	public void remove(Long id) {
		try {
			permissionRepository.deleteById(id);
			permissionRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new PermissionEntityNotExistException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format("Permissão com código %d não pode ser removida, pois está em uso", id));
		}
	}

	public Permission findOrFail(Long id) {
		return permissionRepository.findById(id).orElseThrow(() -> new PermissionEntityNotExistException(id));
	}

}
