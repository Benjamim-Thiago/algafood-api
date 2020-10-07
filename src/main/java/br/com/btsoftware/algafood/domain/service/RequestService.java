package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algafood.domain.exception.RequestEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.repository.RequestRepository;

@Service
public class RequestService {
	
	@Autowired
	private RequestRepository requestRepository;
	
	public Request findOrFail(Long id) {
		return requestRepository.findById(id).orElseThrow(() -> new RequestEntityNotExistException(id));
	}
}
