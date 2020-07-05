package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;
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

	@GetMapping
	public ResponseEntity<List<State>> list() {
		return ResponseEntity.ok(stateRepository.findAll());
	}

	@GetMapping("/{id}")
	public State find(@PathVariable Long id) {
		return stateService.findOrFail(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State save(@RequestBody @Valid State state) {
		return stateService.save(state);
	}

	@PutMapping("/{id}")
	public State update(@PathVariable Long id, @RequestBody @Valid State state) {
		State stateInDatabase = stateService.findOrFail(id);

		BeanUtils.copyProperties(state, stateInDatabase, "id");

		return stateService.save(stateInDatabase);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id) {
		try {
			stateService.remove(id);
			return ResponseEntity.noContent().build();

		} catch (EntityNotExistException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
