package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.PaymentModeEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.PaymentMode;
import br.com.btsoftware.algafood.domain.repository.PaymentModeRepository;

@Service
public class PaymentModeService {

	private static final String PAYMENT_MODE_IN_USE_MESSAGE = "Forma de pagamento de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private PaymentModeRepository paymentModeRepository;

	@Transactional
	public PaymentMode save(PaymentMode paymentMode) {
		return paymentModeRepository.save(paymentMode);
	}

	@Transactional
	public void remove(Long id) {
		try {
			paymentModeRepository.deleteById(id);
			paymentModeRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new PaymentModeEntityNotExistException(id);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(PAYMENT_MODE_IN_USE_MESSAGE,  id));
		}
	}
	
	public PaymentMode findOrFail(Long paymentModeId) {
		return paymentModeRepository.findById(paymentModeId)
				.orElseThrow(() -> new PaymentModeEntityNotExistException(paymentModeId));
	}
}
