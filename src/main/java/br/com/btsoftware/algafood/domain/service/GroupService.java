package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.GroupEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Group;
import br.com.btsoftware.algafood.domain.model.Permission;
import br.com.btsoftware.algafood.domain.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private PermissionService permissionService;
	
	@Transactional
	public Group save(Group group) {

		return groupRepository.save(group);
	}

	@Transactional
	public void remove(Long id) {
		try {
			groupRepository.deleteById(id);
			groupRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GroupEntityNotExistException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format("Group com código %d não pode ser removida, pois está em uso", id));
		}
	}

	public Group findOrFail(Long id) {
		return groupRepository.findById(id).orElseThrow(() -> new GroupEntityNotExistException(id));
	}
	
	@Transactional
	public void disassociatePermission(Long groupId, Long permissionId) {
	    Group group = findOrFail(groupId);
	    Permission permission = permissionService.findOrFail(permissionId);
	    
	    group.removePermission(permission);
	}

	@Transactional
	public void associatePermission(Long groupId, Long permissionId) {
	    Group group = findOrFail(groupId);
	    Permission permission = permissionService.findOrFail(permissionId);
	    
	    group.addPermission(permission);
	}  

}
