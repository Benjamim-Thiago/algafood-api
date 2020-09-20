package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.RequestInput;
import br.com.btsoftware.algafood.domain.model.Request;

@Component
public class RequestInputDisassembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public Request toDomainObject(RequestInput requestInput) {
        return modelMapper.map(requestInput, Request.class);
    }
    
    public void copyToDomainObject(RequestInput requestInput, Request request) {
        modelMapper.map(requestInput, request);
    }   
}
