package br.com.btsoftware.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateInput {
	@NotBlank
	private String name;
		
}
