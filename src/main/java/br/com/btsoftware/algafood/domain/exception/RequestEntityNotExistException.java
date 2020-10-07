package br.com.btsoftware.algafood.domain.exception;

public class RequestEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public RequestEntityNotExistException(String code) {
		super(String.format("Não existe um pedido com código %s", code));
	}
}
