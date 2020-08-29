package br.com.btsoftware.algafood.domain.exception;

public class PaymentModeEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public PaymentModeEntityNotExistException(String message) {
		super(message);
	}
	public PaymentModeEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de forma de pagamento com código %d", id));
	}
}
