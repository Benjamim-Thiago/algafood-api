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

import br.com.btsoftware.algafood.api.assembler.GroupModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.GroupInputDisassembler;
import br.com.btsoftware.algafood.api.model.GroupModel;
import br.com.btsoftware.algafood.api.model.input.GroupInput;
import br.com.btsoftware.algafood.domain.model.Group;
import br.com.btsoftware.algafood.domain.repository.GroupRepository;
import br.com.btsoftware.algafood.domain.service.GroupService;

@RestController
@RequestMapping("groups")
public class GroupController {
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupModelAssembler groupModelAssembler;

	@Autowired
	private GroupInputDisassembler groupInputDisassembler;
	
	@GetMapping
	public List<GroupModel> list() {
		return groupModelAssembler.toCollectionModel(groupRepository.findAll());
	}

	@GetMapping("/{id}")
	public GroupModel find(@PathVariable Long id) {
		return groupModelAssembler.toModel(groupService.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroupModel save(@RequestBody @Valid GroupInput groupInput) {
		Group groupSalved = groupInputDisassembler.toDomainObject(groupInput);
		groupSalved = groupService.save(groupSalved);
		
		return groupModelAssembler.toModel(groupSalved);
	}

	@PutMapping("/{id}")
	public GroupModel update(@PathVariable Long id, @RequestBody @Valid GroupInput groupInput) {
		Group groupInDatabase = groupService.findOrFail(id);

		groupInputDisassembler.copyToDomainObject(groupInput, groupInDatabase);
		
		groupInDatabase = groupService.save(groupInDatabase);
		
		return  groupModelAssembler.toModel(groupInDatabase);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
			groupService.remove(id);
	}

}
