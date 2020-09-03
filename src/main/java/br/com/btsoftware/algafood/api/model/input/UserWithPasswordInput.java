package br.com.btsoftware.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithPasswordInput extends UserInput{
	@NotBlank
	private String password;
}
