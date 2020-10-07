package br.com.btsoftware.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.service.RequestFlowService;

@RestController
@RequestMapping(value = "/requests/{code}")
public class RequestFlowController {
	
	@Autowired
	private RequestFlowService requestFlowService;
	
	@PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confimed(@PathVariable String code) {
		requestFlowService.statusConfirmed(code);
    }
	
	@PutMapping("/cancel")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String code) {
		requestFlowService.cancel(code);
	}

	@PutMapping("/deliver")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliver(@PathVariable String code) {
		requestFlowService.deliver(code);
	}
}
