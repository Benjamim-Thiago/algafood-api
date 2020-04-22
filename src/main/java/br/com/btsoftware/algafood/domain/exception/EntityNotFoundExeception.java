package br.com.btsoftware.algafood.domain.exception;

public class EntityNotFoundExeception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntityNotFoundExeception(String message) {
		super(message);
	}
}
