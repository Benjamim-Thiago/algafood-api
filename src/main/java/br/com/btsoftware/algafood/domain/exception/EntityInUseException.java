package br.com.btsoftware.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityInUseException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EntityInUseException(String message) {
		super(message);
	}
}
