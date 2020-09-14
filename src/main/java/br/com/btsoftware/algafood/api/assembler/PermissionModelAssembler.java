package br.com.btsoftware.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.PermissionModel;
import br.com.btsoftware.algafood.domain.model.Permission;

@Component
public class PermissionModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PermissionModel toModel(Permission permission) {
		return modelMapper.map(permission, PermissionModel.class);
	}
	
	public List<PermissionModel> toCollectionModel(Collection<Permission> permissions) {
		return permissions.stream()
				.map(permission -> toModel(permission))
				.collect(Collectors.toList());
	}
}
