package br.com.btsoftware.algafood.domain.exception;

public class GroupEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public GroupEntityNotExistException(String message) {
		super(message);
	}
	public GroupEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de Grupo com código %d", id));
	}
}
