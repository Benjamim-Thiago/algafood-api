package br.com.btsoftware.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.UserModel;
import br.com.btsoftware.algafood.domain.model.User;

@Component
public class UserModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserModel toModel(User user) {
		return modelMapper.map(user, UserModel.class);
	}
	
	public List<UserModel> toCollectionModel(Collection<User> users) {
		return users.stream()
				.map(user -> toModel(user))
				.collect(Collectors.toList());
	}
}
