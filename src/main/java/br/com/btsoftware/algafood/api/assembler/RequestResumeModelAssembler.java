package br.com.btsoftware.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.RequestResumeModel;
import br.com.btsoftware.algafood.domain.model.Request;

@Component
public class RequestResumeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RequestResumeModel toModel(Request request) {
		return modelMapper.map(request, RequestResumeModel.class);
	}
	
	public List<RequestResumeModel> toCollectionModel(List<Request> requests) {
		return requests.stream()
				.map(request -> toModel(request))
				.collect(Collectors.toList());
	}
}
