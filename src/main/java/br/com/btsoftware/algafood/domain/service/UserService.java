package br.com.btsoftware.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.UserEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.User;
import br.com.btsoftware.algafood.domain.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User save(User user) {
		userRepository.detach(user);
		
		Optional<User> userExist =  userRepository.findByEmail(user.getEmail());
		
		if (userExist.isPresent() && !userExist.get().equals(user)) {
			throw new BusinessException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));	
		}
		
		return userRepository.save(user);
	}


	@Transactional
	public void alterPassword(Long userId, String nowPassword, String newPassword) {

		 User user = findOrFail(userId);
	        
	        if (user.passwordNotWithEquals(nowPassword)) {
	            throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
	        }
	        
	        user.setPassword(newPassword);
	}
	

	public User findOrFail(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserEntityNotExistException(id));
	}

}
