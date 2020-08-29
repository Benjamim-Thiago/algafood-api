package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.PaymentInput;
import br.com.btsoftware.algafood.domain.model.State;

@Component
public class PaymentModeInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public State toDomainObject(PaymentInput paymentInput) {
		return modelMapper.map(paymentInput, State.class);
	}

	public void copyToDomainObject(PaymentInput paymentInput, State state) {
		modelMapper.map(paymentInput, state);
	}
}
