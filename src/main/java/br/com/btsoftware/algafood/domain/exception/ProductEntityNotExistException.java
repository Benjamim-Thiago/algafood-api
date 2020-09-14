package br.com.btsoftware.algafood.domain.exception;

public class ProductEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public ProductEntityNotExistException(String message) {
		super(message);
	}
	public ProductEntityNotExistException(Long restaurantId, Long productId) {
		this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d", 
                productId, restaurantId));

	}
}
