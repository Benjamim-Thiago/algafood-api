package br.com.btsoftware.algafood.domain.exception;

public abstract class EntityNotExistException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public EntityNotExistException(String message) {
		super(message);
	}
}
