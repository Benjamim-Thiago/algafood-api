package br.com.btsoftware.algafood.domain.exception;

public class StateEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public StateEntityNotExistException(String message) {
		super(message);
	}
	public StateEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de Estado com código %d", id));
	}
}
