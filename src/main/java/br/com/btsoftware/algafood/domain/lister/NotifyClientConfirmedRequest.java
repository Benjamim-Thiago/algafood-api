package br.com.btsoftware.algafood.domain.lister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.btsoftware.algafood.domain.event.RequestConfirmedEvent;
import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.service.SendEmailService;
import br.com.btsoftware.algafood.domain.service.SendEmailService.Message;

@Component
public class NotifyClientConfirmedRequest {
	
	@Autowired
	private SendEmailService sendEmailService;
	/***
	 * Metodo 'ao confirmar pedido'
	 * BEFORE DEIXA O METODO SEGUINDO A TRANSAÇÃO DE UPDATE DO PEDIDO
	 */
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void whenConfirmingRequest(RequestConfirmedEvent event) {
		Request request = event.getRequest();
		
		var menssage = Message.builder()
				.subject(request.getRestaurant().getName() + " - Pedido confirmado")
				.body("confirmed_request.html")
				.recipient(request.getClient().getEmail())
				.variable("request", request)
				.build();
		
		sendEmailService.send(menssage);
	}
}
