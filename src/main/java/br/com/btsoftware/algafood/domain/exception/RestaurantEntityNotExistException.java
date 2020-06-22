package br.com.btsoftware.algafood.domain.exception;

public class RestaurantEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public RestaurantEntityNotExistException(String message) {
		super(message);
	}
	public RestaurantEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de Restaurante com código %d", id));
	}
}
