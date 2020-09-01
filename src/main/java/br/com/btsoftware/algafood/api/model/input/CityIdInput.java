package br.com.btsoftware.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityIdInput {
	@NotNull
	private Long id;
}
