package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.repository.RequestRepository;

/***
 * 
 * Serviço: Fluxo de confirmação de processo de pedido
 *
 */
@Service
public class RequestFlowService {
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private RequestService requestService;
		
	@Transactional
	public void statusConfirmed(String code) {
		Request request = requestService.findOrFail(code);
	
		request.confirme();
	
		requestRepository.save(request);

	}
	
	@Transactional
	public void cancel(String code) {
	    Request request = requestService.findOrFail(code);	    
	    request.cancel();
	    
	    requestRepository.save(request);
	}

	@Transactional
	public void deliver(String code) {
	    Request request = requestService.findOrFail(code);
	    request.deliver();
	}
}
