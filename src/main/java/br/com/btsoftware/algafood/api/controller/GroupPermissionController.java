package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.assembler.PermissionModelAssembler;
import br.com.btsoftware.algafood.api.model.PermissionModel;
import br.com.btsoftware.algafood.domain.model.Group;
import br.com.btsoftware.algafood.domain.service.GroupService;

@RestController
@RequestMapping(value = "/groups/{groupId}/permissions")
public class GroupPermissionController {
	@Autowired
	private GroupService groupService;

	@Autowired
	private PermissionModelAssembler permissionModelAssembler;

	@GetMapping
	public List<PermissionModel> list(@PathVariable Long groupId) {
		Group group = groupService.findOrFail(groupId);

		return permissionModelAssembler.toCollectionModel(group.getPermissions());
	}

	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupService.disassociatePermission(groupId, permissionId);
	}

	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupService.associatePermission(groupId, permissionId);
	}
}
