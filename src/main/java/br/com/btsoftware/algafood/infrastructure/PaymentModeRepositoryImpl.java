package br.com.btsoftware.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.PaymentMode;
import br.com.btsoftware.algafood.domain.repository.PaymentModeRepository;

@Component
public class PaymentModeRepositoryImpl implements PaymentModeRepository {
	@PersistenceContext
	private EntityManager manager;

	public List<PaymentMode> list() {
		return manager.createQuery("from PaymentMode", PaymentMode.class).getResultList();
	}

	public PaymentMode find(Long id) {
		return manager.find(PaymentMode.class, id);
	}

	@Transactional
	public PaymentMode save(PaymentMode paymentMode) {
		return manager.merge(paymentMode);
	}

	@Transactional
	public void delete(PaymentMode paymentMode) {
		paymentMode = find(paymentMode.getId());
		manager.remove(paymentMode);

	}
}
