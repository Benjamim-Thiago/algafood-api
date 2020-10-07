package br.com.btsoftware.algafood.domain.model.enumerable;

import java.util.Arrays;
import java.util.List;

public enum RequestStatus {
	CREATED("CRIADO"), 
	CONFIRMED("CONFIRMADO", CREATED), 
	DELIVERED("ENTREGUE", CONFIRMED), 
	CANCELED("CANCELADO", CREATED);
	
	private final String description;
	private List<RequestStatus> oldStatus;
	
	private RequestStatus(String description, RequestStatus ... oldStatus) {
		this.description = description;
		this.oldStatus = Arrays.asList(oldStatus);
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean canNotChangeTo(RequestStatus newStatus) {
		return !newStatus.oldStatus.contains(this);
	}
}
