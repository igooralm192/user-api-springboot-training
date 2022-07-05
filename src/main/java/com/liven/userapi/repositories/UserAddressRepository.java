package com.liven.userapi.repositories;

import com.liven.userapi.models.UserAddress;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserAddressRepository
        extends CrudRepository<UserAddress, Long>, QueryByExampleExecutor<UserAddress> {
}