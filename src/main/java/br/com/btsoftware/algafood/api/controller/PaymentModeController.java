package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	public List<PaymentModeModel> list() {
		return paymentModeModelAssembler.toCollectionModel(paymentModeRepository.findAll());
	}

	@GetMapping("/{id}")
	public PaymentModeModel find(@PathVariable Long id) {
		return paymentModeModelAssembler.toModel(paymentModeService.findOrFail(id));
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
	 * @DeleteMapping("/{id}") public ResponseEntity<PaymentMode> remove(@PathVariable
	 * Long id) { try {
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
