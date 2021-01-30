package br.com.btsoftware.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import br.com.btsoftware.algafood.api.assembler.PaymentModeModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.PaymentModeInputDisassembler;
import br.com.btsoftware.algafood.api.model.PaymentModeModel;
import br.com.btsoftware.algafood.api.model.input.PaymentModeInput;
import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.PaymentModeEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.PaymentMode;
import br.com.btsoftware.algafood.domain.repository.PaymentModeRepository;
import br.com.btsoftware.algafood.domain.service.PaymentModeService;

@RestController
@RequestMapping("/payments")
public class PaymentModeController {

	@Autowired
	private PaymentModeRepository paymentModeRepository;

	@Autowired
	private PaymentModeService paymentModeService;

	@Autowired
	private PaymentModeModelAssembler paymentModeModelAssembler;

	@Autowired
	private PaymentModeInputDisassembler paymentModeInputDisassembler;

	@GetMapping()
	public ResponseEntity<List<PaymentModeModel>> list(ServletWebRequest request) {

		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		OffsetDateTime lastUpdated = paymentModeRepository.getUpdateDateLast();

		if (lastUpdated != null) {
			eTag = String.valueOf(lastUpdated.toEpochSecond());
		}

		if (request.checkNotModified(eTag)) {
			return null;
		}

		List<PaymentMode> allPaymentModes = paymentModeRepository.findAll();

		List<PaymentModeModel> paymentModeModel = paymentModeModelAssembler.toCollectionModel(allPaymentModes);

		return ResponseEntity.ok()				
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//				.cacheControl(CacheControl.noCache())
//				.cacheControl(CacheControl.noStore())
				.eTag(eTag)
				.body(paymentModeModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentModeModel> find(@PathVariable Long id, ServletWebRequest request) {

		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		OffsetDateTime lastUpdated = paymentModeRepository.getUpdateDateLast();

		if (lastUpdated != null) {
			eTag = String.valueOf(lastUpdated.toEpochSecond());
		}

		if (request.checkNotModified(eTag)) {
			return null;
		}

		
		
		PaymentMode paymentMode = paymentModeService.findOrFail(id);

		PaymentModeModel paymentModeModel = paymentModeModelAssembler.toModel(paymentMode);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(paymentModeModel);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentModeModel save(@RequestBody @Valid PaymentModeInput paymentInput) {
		try {

			PaymentMode paymentMode = paymentModeInputDisassembler.toDomainObject(paymentInput);

			return paymentModeModelAssembler.toModel(paymentModeService.save(paymentMode));
		} catch (PaymentModeEntityNotExistException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public PaymentModeModel update(@PathVariable Long id, @RequestBody @Valid PaymentModeInput paymentInput) {
		try {
			PaymentMode paymentModeInDataBase = paymentModeService.findOrFail(id);

			paymentModeInputDisassembler.copyToDomainObject(paymentInput, paymentModeInDataBase);

			return paymentModeModelAssembler.toModel(paymentModeService.save(paymentModeInDataBase));
		} catch (PaymentModeEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

	/*
	 * @DeleteMapping("/{id}") public ResponseEntity<PaymentMode>
	 * remove(@PathVariable Long id) { try {
	 * 
	 * paymentModeService.remove(id); return ResponseEntity.noContent().build();
	 * 
	 * } catch (EntityNotFoundExeception e) {
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * } catch (EntityInUseException e) {
	 * 
	 * return ResponseEntity.status(HttpStatus.CONFLICT).build();
	 * 
	 * } }
	 */

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		paymentModeService.remove(id);
	}
}
