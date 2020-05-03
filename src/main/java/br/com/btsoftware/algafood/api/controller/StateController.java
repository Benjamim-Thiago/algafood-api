package br.com.btsoftware.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotFoundExeception;
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
	public ResponseEntity<State> find(@PathVariable Long id) {
		Optional<State> state = stateRepository.findById(id);

		if (state.isPresent()) {
			return ResponseEntity.ok(state.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody State state) {
		try {

			state = stateService.save(state);

			return ResponseEntity.status(HttpStatus.CREATED).body(state);
		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody State state) {
		try {
			Optional <State> stateInDatabase = stateRepository.findById(id);
			if (stateInDatabase.isPresent()) {
				
				BeanUtils.copyProperties(state, stateInDatabase.get(), "id");
				
				return ResponseEntity.ok(stateService.save(stateInDatabase.get()));				
			}
			return ResponseEntity.notFound().build();
		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove (@PathVariable Long id) {
		try {
			stateService.remove(id);	
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}

}
