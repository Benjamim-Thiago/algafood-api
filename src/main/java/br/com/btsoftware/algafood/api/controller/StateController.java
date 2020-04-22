package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.model.State;
import br.com.btsoftware.algafood.domain.repository.StateRepository;

@RestController
@RequestMapping("states")
public class StateController {
	@Autowired
	private StateRepository stateRepository;
	
	@GetMapping
	public List<State> list () {
		return stateRepository.list();
	}
}
