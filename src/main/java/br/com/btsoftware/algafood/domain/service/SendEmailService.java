package br.com.btsoftware.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface SendEmailService {

	/***
	 *  Metodo de enviar e-mail
	 */
	void send(Message message);
	
	@Getter
	@Builder
	class Message {
		
		/***
		 * Destinatário do e-mail
		 */
		@Singular
		private Set<String> recipients;
		
		/***
		 * Assunto do e-mail
		 */
		@NonNull
		private String subject;
		
		
		/***
		 * Corpo do e-mail
		 */
		@NonNull
		private String body;
		
		@Singular("variable")
		private Map<String, Object> variables;
	}
	
}
