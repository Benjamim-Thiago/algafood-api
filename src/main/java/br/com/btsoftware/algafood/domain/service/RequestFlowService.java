package br.com.btsoftware.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.model.enumerable.RequestStatus;

@Service
public class RequestFlowService {
	
	@Autowired
	private RequestService requestService;
	
	
	@Transactional
	public void statusConfirmed(Long requestId) {
		Request request = requestService.findOrFail(requestId);
		
		if (!request.getRequestStatus().equals(RequestStatus.CREATED)) {
			throw new BusinessException(
					String.format("Status do pedido %d não pode ser alterado de %s para %s",
							request.getId(), request.getRequestStatus().getDescription(), 
							RequestStatus.CONFIRMED.getDescription()));
		}
		
		request.setRequestStatus(RequestStatus.CONFIRMED);
		request.setConfirmationDate(OffsetDateTime.now());
	}
	
	@Transactional
	public void cancel(Long requestId) {
	    Request request = requestService.findOrFail(requestId);
	    
	    if (!request.getRequestStatus().equals(RequestStatus.CREATED)) {
	        throw new BusinessException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        request.getId(), request.getRequestStatus().getDescription(), 
	                        RequestStatus.CANCELED.getDescription()));
	    }
	    
	    request.setRequestStatus(RequestStatus.CANCELED);
	    request.setCancellationDate(OffsetDateTime.now());
	}

	@Transactional
	public void deliver(Long requestId) {
	    Request request = requestService.findOrFail(requestId);
	    
	    if (!request.getRequestStatus().equals(RequestStatus.CONFIRMED)) {
	        throw new BusinessException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        request.getId(), request.getRequestStatus().getDescription(), 
	                        RequestStatus.DELIVERED.getDescription()));
	    }
	    
	    request.setRequestStatus(RequestStatus.DELIVERED);
	    request.setDeliveryDate(OffsetDateTime.now());
	}
}
