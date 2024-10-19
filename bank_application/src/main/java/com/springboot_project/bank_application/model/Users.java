package com.springboot_project.bank_application.model;

import static com.springboot_project.bank_application.repo.UserRepo.CREATED_AT;
import static com.springboot_project.bank_application.repo.UserRepo.EMAIL_ID;
import static com.springboot_project.bank_application.repo.UserRepo.UPDATED_AT;
import static com.springboot_project.bank_application.repo.UserRepo.USERS;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = USERS)
public class Users {

  @Id
  private String id;

  private String username;

  private String lastname;

  @Field(EMAIL_ID)
  private String emailId;

  private String password;

  private Location location;

  @Field(CREATED_AT)
  private LocalDateTime createdAt;

  @Field(UPDATED_AT)
  private LocalDateTime updatedAt;
}
