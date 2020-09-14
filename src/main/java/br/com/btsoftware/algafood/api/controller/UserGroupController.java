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

import br.com.btsoftware.algafood.api.assembler.GroupModelAssembler;
import br.com.btsoftware.algafood.api.model.GroupModel;
import br.com.btsoftware.algafood.domain.model.User;
import br.com.btsoftware.algafood.domain.service.UserService;

@RestController
@RequestMapping(value = "/users/{userId}/groups")
public class UserGroupController {
	@Autowired
	private UserService userService;

	@Autowired
	private GroupModelAssembler groupModelAssembler;

	@GetMapping
	public List<GroupModel> list(@PathVariable Long userId) {
		User user = userService.findOrFail(userId);

		return groupModelAssembler.toCollectionModel(user.getGroups());
	}

	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void diassociate(@PathVariable Long userId, @PathVariable Long groupId) {
		userService.disassociateGroup(userId, groupId);
	}

	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associate(@PathVariable Long userId, @PathVariable Long groupId) {
		userService.associateGroup(userId, groupId);
	}
}
