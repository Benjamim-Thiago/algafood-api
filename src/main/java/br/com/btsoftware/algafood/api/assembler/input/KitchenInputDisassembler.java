package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.KitchenInput;
import br.com.btsoftware.algafood.domain.model.Kitchen;

@Component
public class KitchenInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public Kitchen toDomainObject(KitchenInput kitchenInput) {
		return modelMapper.map(kitchenInput, Kitchen.class);
	}

	public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {
		modelMapper.map(kitchenInput, kitchen);
	}
}
