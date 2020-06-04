package br.com.btsoftware.algafood.domain.model.enumerable;

public enum RequestStatus {
	CREATED("CRIADO"), CONFIRMED("CONFIRMADO"), DELIVERED("ENTREGUE"), CANCELED("CANCELADO");
	
	private final String description;
	
	private RequestStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
