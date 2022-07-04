package com.liven.userapi.repositories;

import com.liven.userapi.models.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends CrudRepository<User, Long>, QueryByExampleExecutor<User> {
  Optional<User> findOneByEmail(String email);
}