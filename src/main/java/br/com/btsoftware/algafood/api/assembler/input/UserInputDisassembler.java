package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.UserInput;
import br.com.btsoftware.algafood.domain.model.User;

@Component
public class UserInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public User toDomainObject(UserInput userInput) {
		return modelMapper.map(userInput, User.class);
	}

	public void copyToDomainObject(UserInput userInput, User user) {
		modelMapper.map(userInput, user);
	}
}
