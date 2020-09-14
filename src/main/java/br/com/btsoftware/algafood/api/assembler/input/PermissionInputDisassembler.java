package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.PermissionInput;
import br.com.btsoftware.algafood.domain.model.Permission;

@Component
public class PermissionInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public Permission toDomainObject(PermissionInput permissionInput) {
		return modelMapper.map(permissionInput, Permission.class);
	}

	public void copyToDomainObject(PermissionInput permissionInput, Permission permission) {
		modelMapper.map(permissionInput, permission);
	}
}
