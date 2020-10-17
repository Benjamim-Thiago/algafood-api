package br.com.btsoftware.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.assembler.RequestModelAssembler;
import br.com.btsoftware.algafood.api.assembler.RequestResumeModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.RequestInputDisassembler;
import br.com.btsoftware.algafood.api.model.RequestModel;
import br.com.btsoftware.algafood.api.model.RequestResumeModel;
import br.com.btsoftware.algafood.api.model.input.RequestInput;
import br.com.btsoftware.algafood.core.data.PageableTranslator;
import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Request;
import br.com.btsoftware.algafood.domain.model.User;
import br.com.btsoftware.algafood.domain.repository.RequestRepository;
import br.com.btsoftware.algafood.domain.repository.filter.RequestFilter;
import br.com.btsoftware.algafood.domain.service.RequestIssueService;
import br.com.btsoftware.algafood.domain.service.RequestService;
import br.com.btsoftware.algafood.infrastructure.repository.spec.RequestSpecs;

@RestController
@RequestMapping(value = "/requests")
public class RequestController {
	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private RequestService requestService;

	@Autowired
	private RequestIssueService issueRequestService;

	@Autowired
	private RequestResumeModelAssembler requestResumeModelAssembler;

	@Autowired
	private RequestModelAssembler requestModelAssembler;

	@Autowired
	private RequestInputDisassembler requestInputDisassembler;

	@GetMapping
	public Page<RequestResumeModel> search(RequestFilter filter, @PageableDefault(size = 10) Pageable pageable) {

		pageable = translatePageable(pageable);

		Page<Request> allRequests = requestRepository.findAll(RequestSpecs.searchUseFilter(filter), pageable);

		List<RequestResumeModel> requestModel = requestResumeModelAssembler.toCollectionModel(allRequests.getContent());

		Page<RequestResumeModel> requestModelPage = new PageImpl<>(requestModel, pageable,
				allRequests.getTotalElements());

		return requestModelPage;
	}

	@GetMapping("/{code}")
	public RequestModel find(@PathVariable String code) {
		Request request = requestService.findOrFail(code);

		return requestModelAssembler.toModel(request);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RequestModel save(@Valid @RequestBody RequestInput requestInput) {
		try {
			Request newRequest = requestInputDisassembler.toDomainObject(requestInput);

			// TODO pegar usuário autenticado
			newRequest.setClient(new User());
			newRequest.getClient().setId(1L);

			newRequest = issueRequestService.save(newRequest);

			return requestModelAssembler.toModel(newRequest);
		} catch (EntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	private Pageable translatePageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"code", "code", 
				"restaurant.name", "restaurant.name",
				"client.name","client.name", 
				"valueTotal", "valueTotal",
				"subTotal", "subTotal",
				"deliveryFee", "deliveryFee",
				"requestStatus", "requestStatus"
			);

		return PageableTranslator.translate(apiPageable, mapeamento);
	}
}
