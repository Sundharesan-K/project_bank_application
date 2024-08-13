package com.springboot_project.bank_application.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class Users {
  @Id
  private String id;
  private String username;
  private String lastname;
  private String emailId;
  private String password;
  private LocalDateTime createTs;
  private LocalDateTime updateTs;
}
