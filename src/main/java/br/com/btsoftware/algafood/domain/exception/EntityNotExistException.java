package br.com.btsoftware.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class EntityNotExistException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public EntityNotExistException(String message) {
		super(message);
	}
}
