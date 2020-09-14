package br.com.btsoftware.algafood.domain.exception;

public class PermissionEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public PermissionEntityNotExistException(String message) {
		super(message);
	}
	public PermissionEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de permissão com código %d", id));
	}
}
