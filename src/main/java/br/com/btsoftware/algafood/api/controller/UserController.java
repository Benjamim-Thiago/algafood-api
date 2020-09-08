package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.assembler.UserModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.UserInputDisassembler;
import br.com.btsoftware.algafood.api.model.UserModel;
import br.com.btsoftware.algafood.api.model.input.UserAlterPassswordInput;
import br.com.btsoftware.algafood.api.model.input.UserInput;
import br.com.btsoftware.algafood.api.model.input.UserWithPasswordInput;
import br.com.btsoftware.algafood.domain.model.User;
import br.com.btsoftware.algafood.domain.repository.UserRepository;
import br.com.btsoftware.algafood.domain.service.UserService;

@RestController
@RequestMapping(value = "users")
public class UserController {
	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserModelAssembler userModelAssembler;
    
    @Autowired
    private UserInputDisassembler userInputDisassembler;
    
    @GetMapping
    public List<UserModel> list() {
        List<User> todasUsers = userRepository.findAll();
        
        return userModelAssembler.toCollectionModel(todasUsers);
    }
    
    @GetMapping("/{userId}")
    public UserModel find(@PathVariable Long userId) {
        User user = userService.findOrFail(userId);
        
        return userModelAssembler.toModel(user);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel save(@RequestBody @Valid UserWithPasswordInput userInput) {
        User user = userInputDisassembler.toDomainObject(userInput);
        user = userService.save(user);
        
        return userModelAssembler.toModel(user);
    }
    
    @PutMapping("/{userId}")
    public UserModel update(@PathVariable Long userId,
            @RequestBody @Valid UserInput userInput) {
    	
        User userNow = userService.findOrFail(userId);
        userInputDisassembler.copyToDomainObject(userInput, userNow);
        userNow = userService.save(userNow);
        
        return userModelAssembler.toModel(userNow);
    }
    
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterPassword(@PathVariable Long userId, @RequestBody @Valid UserAlterPassswordInput password) {
    	userService.alterPassword(userId, password.getCurrentPassword(), password.getNewPassword());
    }            
}
