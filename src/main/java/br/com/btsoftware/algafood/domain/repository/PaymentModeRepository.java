package br.com.btsoftware.algafood.domain.repository;

import java.util.List;

import br.com.btsoftware.algafood.domain.model.PaymentMode;

public interface PaymentModeRepository {
	List<PaymentMode> list();
	PaymentMode find(Long id);
	PaymentMode save(PaymentMode paymentMode);
	void delete(PaymentMode paymentMode);
}
