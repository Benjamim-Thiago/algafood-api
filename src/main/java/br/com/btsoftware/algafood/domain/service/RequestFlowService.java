package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.service.SendEmailService.Message;

/***
 * 
 * Serviço: Fluxo de confirmação de processo de pedido
 *
 */
@Service
public class RequestFlowService {
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private SendEmailService sendEmail;
	
	@Transactional
	public void statusConfirmed(String code) {
		Request request = requestService.findOrFail(code);
	
		request.confirme();
	
	var menssage = Message.builder()
				.subject(request.getRestaurant().getName() + " - Pedido confirmado")
				.body("confirmed_request.html")
				.recipient(request.getClient().getEmail())
				.variable("request", request)
				.build();
		
		sendEmail.send(menssage);
	}
	
	@Transactional
	public void cancel(String code) {
	    Request request = requestService.findOrFail(code);	    
	    request.cancel();
	}

	@Transactional
	public void deliver(String code) {
	    Request request = requestService.findOrFail(code);
	    request.deliver();
	}
}
