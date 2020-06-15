package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
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

	@GetMapping
	public ResponseEntity<List<City>> list() {
		return ResponseEntity.ok(cityRepository.findAll());
	}

	@GetMapping("/{id}")
	public City find(@PathVariable Long id) {
		return cityService.findOrFail(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public City save(@RequestBody City city) {
		try {
			return cityService.save(city);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public City update(@PathVariable Long id, @RequestBody City city) {
		City cityInDatabase = cityService.findOrFail(id);
		BeanUtils.copyProperties(city, cityInDatabase, "id");

		try {
			return cityService.save(cityInDatabase);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<City> remover(@PathVariable Long id) {
		try {
			cityService.remove(id);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
