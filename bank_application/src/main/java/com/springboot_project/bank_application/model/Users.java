package com.springboot_project.bank_application.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "users")
public class Users {

  @Id
  private String id;

  private String username;

  private String lastname;

  @Field("email_id")
  private String emailId;

  private String password;

  private Location location;

  @Field("created_at")
  private LocalDateTime createdAt;

  @Field("updated_at")
  private LocalDateTime updatedAt;
}
