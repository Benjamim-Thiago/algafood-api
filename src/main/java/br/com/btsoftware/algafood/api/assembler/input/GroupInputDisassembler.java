package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.GroupInput;
import br.com.btsoftware.algafood.domain.model.Group;

@Component
public class GroupInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public Group toDomainObject(GroupInput groupInput) {
		return modelMapper.map(groupInput, Group.class);
	}

	public void copyToDomainObject(GroupInput groupInput, Group group) {
		modelMapper.map(groupInput, group);
	}
}
