package br.com.btsoftware.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algafood.domain.model.PaymentMode;

public interface PaymentModeRepository  extends JpaRepository<PaymentMode, Long> {
	
}
