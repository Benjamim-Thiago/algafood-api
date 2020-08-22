package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.StateInput;
import br.com.btsoftware.algafood.domain.model.State;

@Component
public class StateInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public State toDomainObject(StateInput stateInput) {
		return modelMapper.map(stateInput, State.class);
	}

	public void copyToDomainObject(StateInput stateInput, State state) {
		modelMapper.map(stateInput, state);
	}
}
