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

import br.com.btsoftware.algafood.api.assembler.CityModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.CityInputDisassembler;
import br.com.btsoftware.algafood.api.exceptionhandler.Problem;
import br.com.btsoftware.algafood.api.model.CityModel;
import br.com.btsoftware.algafood.api.model.input.CityInput;
import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.StateEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.repository.CityRepository;
import br.com.btsoftware.algafood.domain.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cities")
@Api(tags = "Cidades")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityService cityService;

	@Autowired
	private CityModelAssembler cityModelAssembler;

	@Autowired
	private CityInputDisassembler cityInputDisassembler;

	@GetMapping
	@ApiOperation("LISTA TODAS AS CIDADES")
	public List<CityModel> list() {
		return cityModelAssembler.toCollectionModel(cityRepository.findAll());
	}

	@GetMapping("/{id}")
	@ApiOperation("BUSCA UMA CIDADE POR ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	public CityModel find(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {
		return cityModelAssembler.toModel(cityService.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("CRIA UMA NOVA CIDADE")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada"), })
	public CityModel save(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CityInput cityInput) {
		try {

			City city = cityInputDisassembler.toDomainObject(cityInput);

			return cityModelAssembler.toModel(cityService.save(city));

		} catch (StateEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation("ATUALIZA UMA CIDADE POR ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	public CityModel update(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id,

			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") @RequestBody @Valid CityInput cityInput) {
		try {
			City cityInDatabase = cityService.findOrFail(id);

			cityInputDisassembler.copyToDomainObject(cityInput, cityInDatabase);

			return cityModelAssembler.toModel(cityService.save(cityInDatabase));

		} catch (StateEntityNotExistException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation("EXCLUI UMA CIDADE POR ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cidade excluída"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {

		cityService.remove(id);
	}

}
