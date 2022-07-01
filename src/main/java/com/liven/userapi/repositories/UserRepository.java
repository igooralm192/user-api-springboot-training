package com.liven.userapi.repositories;

import com.liven.userapi.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}