package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.PaymentInput;
import br.com.btsoftware.algafood.domain.model.PaymentMode;

@Component
public class PaymentModeInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public PaymentMode toDomainObject(PaymentInput paymentInput) {
		return modelMapper.map(paymentInput, PaymentMode.class);
	}

	public void copyToDomainObject(PaymentInput paymentInput, PaymentMode payment) {
		modelMapper.map(paymentInput, payment);
	}
}
