package br.com.btsoftware.algafood.domain.event;

import br.com.btsoftware.algafood.domain.model.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestConfirmedEvent {

	private Request request;
	
}
