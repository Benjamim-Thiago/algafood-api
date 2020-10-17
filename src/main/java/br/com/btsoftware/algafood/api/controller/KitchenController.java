package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.btsoftware.algafood.api.assembler.KitchenModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.KitchenInputDisassembler;
import br.com.btsoftware.algafood.api.model.KitchenModel;
import br.com.btsoftware.algafood.api.model.input.KitchenInput;
import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.KitchenEntityNotExistException;
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

	@Autowired
	private KitchenModelAssembler kitchenModelAssembler;

	@Autowired
	private KitchenInputDisassembler kitchenInputDisassembler;

	@GetMapping()
	public Page<KitchenModel> list(@PageableDefault(size = 10) Pageable pageable) {
		Page<Kitchen> kitchens =  kitchenRepository.findAll(pageable);
		
		List<KitchenModel> kitchensModel = kitchenModelAssembler.toCollectionModel(kitchens.getContent()); 
		
		Page<KitchenModel> kitchensModelPage = new PageImpl<>(kitchensModel, pageable, 
				kitchens.getTotalElements());
		
		return kitchensModelPage;
	
	}

	@GetMapping("/{id}")
	public KitchenModel find(@PathVariable Long id) {
		return kitchenModelAssembler.toModel(kitchenService.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public KitchenModel save(@RequestBody @Valid KitchenInput kitchenInput) {
		try {

			Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);

			return kitchenModelAssembler.toModel(kitchenService.save(kitchen));
		} catch (KitchenEntityNotExistException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public KitchenModel update(@PathVariable Long id, @RequestBody @Valid KitchenInput kitchenInput) {
		try {
			Kitchen kitchenInDataBase = kitchenService.findOrFail(id);

			kitchenInputDisassembler.copyToDomainObject(kitchenInput, kitchenInDataBase);

			return kitchenModelAssembler.toModel(kitchenService.save(kitchenInDataBase));
		} catch (KitchenEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}

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
