package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.GroupEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Group;
import br.com.btsoftware.algafood.domain.repository.GroupRepository;

@Service
public class GroupService {

	private static final String STATE_IN_USE_MESSAGE = "Estado com código %d não pode ser removida, pois está em uso";

	@Autowired
	private GroupRepository groupRepository;

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
			throw new EntityInUseException(String.format(STATE_IN_USE_MESSAGE, id));
		}
	}

	public Group findOrFail(Long id) {
		return groupRepository.findById(id).orElseThrow(() -> new GroupEntityNotExistException(id));
	}

}
