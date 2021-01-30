package br.com.btsoftware.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.btsoftware.algafood.domain.model.PaymentMode;

public interface PaymentModeRepository  extends JpaRepository<PaymentMode, Long> {
	
	@Query("select max(updated) from PaymentMode")
	OffsetDateTime getUpdateDateLast();
}
