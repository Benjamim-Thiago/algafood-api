package br.com.btsoftware.algafood.domain.exception;

public class KitchenEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public KitchenEntityNotExistException(String message) {
		super(message);
	}
	public KitchenEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de cozinha com código %d", id));
	}
}
