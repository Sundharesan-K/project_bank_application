package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {

  String USERS = "users";
  String EMAIL_ID = "email_id";
  String CREATED_AT = "created_at";
  String UPDATED_AT = "updated_at";

  Users findByEmailId(String emailId);
}
