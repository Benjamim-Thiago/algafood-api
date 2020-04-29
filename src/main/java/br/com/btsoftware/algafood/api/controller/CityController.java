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
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotFoundExeception;
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
		return ResponseEntity.ok(cityRepository.list());
	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<City> find(@PathVariable Long id) {
		City city = cityRepository.find(id);

		if (city != null) {
			return ResponseEntity.ok(city);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody City city) {
		try {

			city = cityService.save(city);

			return ResponseEntity.status(HttpStatus.CREATED).body(city);
		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
		try {

			City cityInDatabase =  cityRepository.find(id);
			if (cityInDatabase != null) {
				BeanUtils.copyProperties(city, cityInDatabase, "id");

				city = cityService.save(cityInDatabase);
				return ResponseEntity.ok(city);
				
			}
			
			return ResponseEntity.notFound().build();

		} catch (EntityNotFoundExeception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
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
