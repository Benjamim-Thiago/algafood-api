package br.com.btsoftware.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundExeception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntityNotFoundExeception(String message) {
		super(message);
	}
}
