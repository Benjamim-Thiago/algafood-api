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

import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.repository.KitchenRepository;
import br.com.btsoftware.algafood.domain.service.KitchenService;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private KitchenService kitchenService;

	@GetMapping()
	public List<Kitchen> list() {
		return kitchenRepository.findAll();
	}

	@GetMapping("/{id}")
	public Kitchen find(@PathVariable Long id) {
		return kitchenService.findOrFail(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Kitchen> save(@RequestBody @Valid Kitchen kitchen) {
		Kitchen kitchenSaved = kitchenService.save(kitchen);

		return ResponseEntity.ok(kitchenSaved);
	}

	@PutMapping("/{id}")
	public Kitchen update(@PathVariable Long id, @RequestBody @Valid Kitchen kitchen) {
		Kitchen kitchenInDataBase = kitchenService.findOrFail(id);
		BeanUtils.copyProperties(kitchen, kitchenInDataBase, "id");
		return kitchenService.save(kitchenInDataBase);

	}

	/*
	 * @DeleteMapping("/{id}") public ResponseEntity<Kitchen> remove(@PathVariable
	 * Long id) { try {
	 * 
	 * kitchenService.remove(id); return ResponseEntity.noContent().build();
	 * 
	 * } catch (EntityNotFoundExeception e) {
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * } catch (EntityInUseException e) {
	 * 
	 * return ResponseEntity.status(HttpStatus.CONFLICT).build();
	 * 
	 * } }
	 */

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void remove(@PathVariable Long id) {
		kitchenService.remove(id);
	}
}
