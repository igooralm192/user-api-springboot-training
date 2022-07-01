package com.liven.userapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liven.userapi.models.User;
import com.liven.userapi.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping()
  public Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  @PostMapping()
  public User createUser(@RequestBody User user) {
    return userRepository.save(user);
  }
}
