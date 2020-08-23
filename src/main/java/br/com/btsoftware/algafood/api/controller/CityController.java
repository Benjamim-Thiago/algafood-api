package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import br.com.btsoftware.algafood.api.assembler.CityModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.CityInputDisassembler;
import br.com.btsoftware.algafood.api.model.CityModel;
import br.com.btsoftware.algafood.api.model.input.CityInput;
import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;
import br.com.btsoftware.algafood.domain.exception.StateEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.repository.CityRepository;
import br.com.btsoftware.algafood.domain.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityService cityService;

	@Autowired
	private CityModelAssembler cityModelAssembler;

	@Autowired
	private CityInputDisassembler cityInputDisassembler;

	@GetMapping
	public List<CityModel> list() {
		return cityModelAssembler.toCollectionModel(cityRepository.findAll());
	}

	@GetMapping("/{id}")
	public CityModel find(@PathVariable Long id) {
		return cityModelAssembler.toModel(cityService.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityModel save(@RequestBody @Valid CityInput cityInput) {
		try {

			City city = cityInputDisassembler.toDomainObject(cityInput);

			return cityModelAssembler.toModel(cityService.save(city));

		} catch (StateEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public CityModel update(@PathVariable Long id, @RequestBody @Valid CityInput cityInput) {
		try {
			City cityInDatabase = cityService.findOrFail(id);

			cityInputDisassembler.copyToDomainObject(cityInput, cityInDatabase);

			return cityModelAssembler.toModel(cityService.save(cityInDatabase));

		} catch (StateEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<City> remover(@PathVariable Long id) {
		try {
			cityService.remove(id);
			return ResponseEntity.noContent().build();

		} catch (EntityNotExistException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
