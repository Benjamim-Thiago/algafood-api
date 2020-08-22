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

import br.com.btsoftware.algafood.api.assembler.StateModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.StateInputDisassembler;
import br.com.btsoftware.algafood.api.model.StateModel;
import br.com.btsoftware.algafood.api.model.input.StateInput;
import br.com.btsoftware.algafood.domain.model.State;
import br.com.btsoftware.algafood.domain.repository.StateRepository;
import br.com.btsoftware.algafood.domain.service.StateService;

@RestController
@RequestMapping("states")
public class StateController {
	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateService stateService;
	
	@Autowired
	private StateModelAssembler stateModelAssembler;

	@Autowired
	private StateInputDisassembler stateInputDisassembler;
	
	@GetMapping
	public List<StateModel> list() {
		return stateModelAssembler.toCollectionModel(stateRepository.findAll());
	}

	@GetMapping("/{id}")
	public StateModel find(@PathVariable Long id) {
		return stateModelAssembler.toModel(stateService.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateModel save(@RequestBody @Valid StateInput stateInput) {
		State stateSalved = stateInputDisassembler.toDomainObject(stateInput);
		stateSalved = stateService.save(stateSalved);
		
		return stateModelAssembler.toModel(stateSalved);
	}

	@PutMapping("/{id}")
	public StateModel update(@PathVariable Long id, @RequestBody @Valid StateInput stateInput) {
		State stateInDatabase = stateService.findOrFail(id);

		stateInputDisassembler.copyToDomainObject(stateInput, stateInDatabase);
		
		stateInDatabase = stateService.save(stateInDatabase);
		
		return  stateModelAssembler.toModel(stateInDatabase);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
			stateService.remove(id);
	}

}
