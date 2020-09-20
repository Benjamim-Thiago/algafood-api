package br.com.btsoftware.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.RequestModel;
import br.com.btsoftware.algafood.domain.model.Request;

@Component
public class RequestModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RequestModel toModel(Request request) {
		return modelMapper.map(request, RequestModel.class);
	}
	
	public List<RequestModel> toCollectionModel(Collection<Request> requests) {
		return requests.stream()
				.map(request -> toModel(request))
				.collect(Collectors.toList());
	}
}
