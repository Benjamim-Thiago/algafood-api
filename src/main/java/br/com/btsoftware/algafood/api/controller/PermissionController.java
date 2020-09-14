package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.assembler.PermissionModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.PermissionInputDisassembler;
import br.com.btsoftware.algafood.api.model.PermissionModel;
import br.com.btsoftware.algafood.api.model.input.PermissionInput;
import br.com.btsoftware.algafood.domain.model.Permission;
import br.com.btsoftware.algafood.domain.repository.PermissionRepository;
import br.com.btsoftware.algafood.domain.service.PermissionService;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private PermissionModelAssembler permissionModelAssembler;
	
	@Autowired
	private PermissionInputDisassembler permissionInputDisassembler;
	
	@GetMapping
	public List<PermissionModel> list() {
		return permissionModelAssembler.toCollectionModel(permissionRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public PermissionModel find(@PathVariable Long id) {
		Permission permission = permissionService.findOrFail(id);
		return permissionModelAssembler.toModel(permission);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissionModel save(@RequestBody @Valid PermissionInput permissionInput) {
		Permission permissionSalved = permissionInputDisassembler.toDomainObject(permissionInput);
		permissionSalved = permissionService.save(permissionSalved);
		
		return permissionModelAssembler.toModel(permissionSalved);
	}
	
	@PutMapping("/{id}")
	public PermissionModel update(@PathVariable Long id, @RequestBody @Valid PermissionInput permissionInput) {
		Permission permissionInDatabase = permissionService.findOrFail(id);

		permissionInputDisassembler.copyToDomainObject(permissionInput, permissionInDatabase);
		
		permissionInDatabase = permissionService.save(permissionInDatabase);
		
		return  permissionModelAssembler.toModel(permissionInDatabase);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
			permissionService.remove(id);
	}

}
