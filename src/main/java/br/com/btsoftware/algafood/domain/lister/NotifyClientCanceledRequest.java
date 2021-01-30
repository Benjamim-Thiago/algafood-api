package br.com.btsoftware.algafood.domain.lister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.btsoftware.algafood.domain.event.RequestCanceledEvent;
import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.service.SendEmailService;
import br.com.btsoftware.algafood.domain.service.SendEmailService.Message;

@Component
public class NotifyClientCanceledRequest {
	
	@Autowired
	private SendEmailService sendEmailService;
	/***
	 * Metodo 'ao cancelar pedido'
	 * BEFORE DEIXA O METODO SEGUINDO A TRANSAÇÃO DE UPDATE DO PEDIDO
	 */
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void whenCancelRequest(RequestCanceledEvent event) {
		Request request = event.getRequest();
		
		var menssage = Message.builder()
				.subject(request.getRestaurant().getName() + " - Pedido cancelado")
				.body("canceled_request.html")
				.recipient(request.getClient().getEmail())
				.variable("request", request)
				.variable("reason", "Pedido foi recusado por falta de produto") //Aqui eu poderia ter uma tabela de motivos no banco (Model)
				.build();
		
		sendEmailService.send(menssage);
	}
}
