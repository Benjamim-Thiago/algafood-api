package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.Request;

@Service
public class RequestFlowService {
	
	@Autowired
	private RequestService requestService;
	
	
	@Transactional
	public void statusConfirmed(Long requestId) {
		Request request = requestService.findOrFail(requestId);
		request.confirme();
	}
	
	@Transactional
	public void cancel(Long requestId) {
	    Request request = requestService.findOrFail(requestId);	    
	    request.cancel();
	}

	@Transactional
	public void deliver(Long requestId) {
	    Request request = requestService.findOrFail(requestId);
	    request.deliver();
	}
}
