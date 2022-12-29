package com.firstock.employee.repository;

import java.io.Serializable;

import com.firstock.employee.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);
}