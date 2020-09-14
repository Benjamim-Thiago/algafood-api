package br.com.btsoftware.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.PaymentModeModel;
import br.com.btsoftware.algafood.domain.model.PaymentMode;

@Component
public class PaymentModeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PaymentModeModel toModel(PaymentMode paymentMode) {
		return modelMapper.map(paymentMode, PaymentModeModel.class);
	}
	
	public List<PaymentModeModel> toCollectionModel(Collection<PaymentMode> payments) {
		return payments.stream()
				.map(request -> toModel(request))
				.collect(Collectors.toList());
	}
}
