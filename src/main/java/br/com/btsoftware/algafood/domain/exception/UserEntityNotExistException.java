package br.com.btsoftware.algafood.domain.exception;

public class UserEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public UserEntityNotExistException(String message) {
		super(message);
	}
	public UserEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de Usuário com código %d", id));
	}
}
