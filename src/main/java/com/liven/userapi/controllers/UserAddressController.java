package com.liven.userapi.controllers;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liven.userapi.dtos.CreateUserAddressDTO;
import com.liven.userapi.dtos.UpdateUserAddressDTO;
import com.liven.userapi.exceptions.BaseException;
import com.liven.userapi.models.User;
import com.liven.userapi.models.UserAddress;
import com.liven.userapi.repositories.UserAddressRepository;
import com.liven.userapi.repositories.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users/{userId}/addresses")
public class UserAddressController {
  @Autowired
  private UserRepository userRepository;

  // TODO: Isolate on service
  @Autowired
  private UserAddressRepository userAddressRepository;

  @GetMapping()
  public Iterable<UserAddress> getAllUserAddresses(
      @PathVariable("userId") Long userId,
      @RequestParam Map<String, String> allParams) {
    ExampleMatcher queryMatcher = ExampleMatcher.matchingAll()
        .withMatcher("streetName", GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("streetNumber", GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("zipCode", GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("city", GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("state", GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("country", GenericPropertyMatchers.exact().ignoreCase())
        .withMatcher("complement", GenericPropertyMatchers.contains().ignoreCase());

    User queryUser = User.builder().id(userId).build();

    UserAddress queryUserAddress = UserAddress.builder()
        .streetName(allParams.get("streetName"))
        .streetNumber(allParams.get("streetNumber"))
        .zipCode(allParams.get("zipCode"))
        .city(allParams.get("city"))
        .state(allParams.get("state"))
        .country(allParams.get("country"))
        .complement(allParams.get("complement"))
        .user(queryUser)
        .build();

    return userAddressRepository.findAll(Example.of(queryUserAddress, queryMatcher));
  }

  @GetMapping("{id}")
  public UserAddress getUserAddress(
      @PathVariable("userId") Long userId,
      @PathVariable("id") Long id) throws BaseException {
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new BaseException("Usuário não encontrado.", 404);
    }

    Optional<UserAddress> userAddress = userAddressRepository.findById(id);

    if (!userAddress.isPresent()) {
      throw new BaseException("Endereço não encontrado.", 404);
    }

    return userAddress.get();
  }

  @PostMapping()
  public UserAddress createUserAddress(
      @PathVariable("userId") Long userId,
      @RequestBody @Valid CreateUserAddressDTO userAddressData) throws BaseException {
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new BaseException("Usuário não encontrado.", 404);
    }

    UserAddress userAddress = new UserAddress();
    userAddress.setStreetName(userAddressData.streetName);
    userAddress.setStreetNumber(userAddressData.streetNumber);
    userAddress.setZipCode(userAddressData.zipCode);
    userAddress.setCity(userAddressData.city);
    userAddress.setState(userAddressData.state);
    userAddress.setCountry(userAddressData.country);
    userAddress.setComplement(userAddressData.complement);
    userAddress.setUser(user.get());

    return userAddressRepository.save(userAddress);
  }

  @PatchMapping("{id}")
  public UserAddress updateUserAddress(
      @PathVariable("userId") Long userId,
      @PathVariable("id") Long id,
      @RequestBody @Valid UpdateUserAddressDTO userAddressData) throws BaseException {
    Optional<User> findUser = this.userRepository.findById(userId);

    if (!findUser.isPresent()) {
      throw new BaseException("Usuário não encontrado.", 404);
    }

    Optional<UserAddress> findUserAddress = userAddressRepository.findById(id);

    if (!findUserAddress.isPresent()) {
      throw new BaseException("Endereço não encontrado.", 404);
    }

    UserAddress userAddress = findUserAddress.get();
    userAddress.setStreetName(userAddressData.streetName);
    userAddress.setStreetNumber(userAddressData.streetNumber);
    userAddress.setZipCode(userAddressData.zipCode);
    userAddress.setCity(userAddressData.city);
    userAddress.setState(userAddressData.state);
    userAddress.setCountry(userAddressData.country);
    userAddress.setComplement(userAddressData.complement);

    return userAddressRepository.save(userAddress);
  }

  @DeleteMapping("{id}")
  public void deleteUser(
      @PathVariable("userId") Long userId,
      @PathVariable("id") Long id) throws BaseException {
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new BaseException("Usuário não encontrado.", 404);
    }

    Optional<UserAddress> userAddress = userAddressRepository.findById(id);

    if (!userAddress.isPresent()) {
      throw new BaseException("Endereço não encontrado.", 404);
    }
    
    userAddressRepository.delete(userAddress.get());
  }
}
