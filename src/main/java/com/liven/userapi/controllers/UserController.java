package com.liven.userapi.controllers;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liven.userapi.dtos.CreateUserDTO;
import com.liven.userapi.exceptions.BaseException;
import com.liven.userapi.models.User;
import com.liven.userapi.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
  // TODO: Isolate on service
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping()
  public Iterable<User> getAllUsers(@RequestParam Map<String, String> allParams) {
    ExampleMatcher queryMatcher = ExampleMatcher.matchingAll()
        .withMatcher("name", GenericPropertyMatchers.contains().ignoreCase());

    User queryUser = User.builder()
        .name(allParams.get("name"))
        .email(allParams.get("email"))
        .build();

    return userRepository.findAll(Example.of(queryUser, queryMatcher));
  }

  @GetMapping("{id}")
  public User getUser(@PathVariable Long id) throws BaseException {
    Optional<User> user = userRepository.findById(id);

    if (!user.isPresent()) {
      throw new BaseException("Usuário não encontrado.", 404);
    }

    return user.get();
  }

  @PostMapping()
  public User createUser(@RequestBody @Valid CreateUserDTO userData) throws BaseException {
    Optional<User> findUser = this.userRepository.findOneByEmail(userData.email);

    if (findUser.isPresent()) {
      throw new BaseException("Usuário já existe.", 400);
    }

    User user = new User();
    user.setName(userData.name);
    user.setEmail(userData.email);
    user.setPassword(passwordEncoder.encode(userData.password));

    return userRepository.save(user);
  }
}
