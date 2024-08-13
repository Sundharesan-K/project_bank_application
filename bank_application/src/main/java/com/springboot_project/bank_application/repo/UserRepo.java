package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {

  Users findByEmailId(String username);
}
