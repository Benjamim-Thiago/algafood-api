package br.com.btsoftware.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.UserEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Group;
import br.com.btsoftware.algafood.domain.model.User;
import br.com.btsoftware.algafood.domain.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public User save(User user) {
		userRepository.detach(user);
		
		Optional<User> userExist =  userRepository.findByEmail(user.getEmail());
		
		if (userExist.isPresent() && !userExist.get().equals(user)) {
			throw new BusinessException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));	
		}
		
		if (user.isNew()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));			
		}
		
		return userRepository.save(user);
	}


	@Transactional
	public void alterPassword(Long userId, String nowPassword, String newPassword) {

		 User user = findOrFail(userId);
	        
	        if (!passwordEncoder.matches(nowPassword, user.getPassword())) {
	            throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
	        }
	        
	        user.setPassword(passwordEncoder.encode(newPassword));
	}
	

	public User findOrFail(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserEntityNotExistException(id));
	}
	
	@Transactional
	public void disassociateGroup(Long userId, Long groupId) {
	    User user = findOrFail(userId);
	    Group group = groupService.findOrFail(groupId);
	    
	    user.removeGroup(group);
	}

	@Transactional
	public void associateGroup(Long userId, Long groupId) {
	    User user = findOrFail(userId);
	    Group group = groupService.findOrFail(groupId);
	    
	    user.addGroup(group);
	}

}
