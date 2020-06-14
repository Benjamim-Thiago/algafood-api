package br.com.btsoftware.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundExeception extends ResponseStatusException {
	private static final long serialVersionUID = 1L;

	public EntityNotFoundExeception(HttpStatus status, String message) {
		super(status, message);
	}

	public EntityNotFoundExeception(String message) {
		this(HttpStatus.NOT_FOUND, message);
	}
}
