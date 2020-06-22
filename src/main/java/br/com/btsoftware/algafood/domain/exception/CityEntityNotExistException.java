package br.com.btsoftware.algafood.domain.exception;

public class CityEntityNotExistException extends EntityNotExistException {
	private static final long serialVersionUID = 1L;

	public CityEntityNotExistException(String message) {
		super(message);
	}
	public CityEntityNotExistException(Long id) {
		this(String.format("Não existe um cadastro de Cidade com código %d", id));
	}
}
