package br.com.surb.todolist.user.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.surb.todolist.user.entities.UserModel;
import br.com.surb.todolist.user.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final IUserRepository userRepository;

  public UserController(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping
  public ResponseEntity create(@RequestBody UserModel userModel){
    UserModel userName = userRepository.findByUsername(userModel.getUsername());

    if(userName != null){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
    }

    String passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

    userModel.setPassword(passwordHashred);
    UserModel user = userRepository.save(userModel);
    System.out.println(userModel.getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }
}
