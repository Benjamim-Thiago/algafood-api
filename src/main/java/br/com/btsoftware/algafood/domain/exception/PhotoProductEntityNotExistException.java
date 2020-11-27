package br.com.btsoftware.algafood.domain.exception;

public class PhotoProductEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public PhotoProductEntityNotExistException(String message) {
		super(message);
	}
	public PhotoProductEntityNotExistException(Long restaurantId, Long productId) {
		this(String.format("Não existe uma foto para o produto com código %d do restaurante de código %d", 
                productId, restaurantId));

	}
}
