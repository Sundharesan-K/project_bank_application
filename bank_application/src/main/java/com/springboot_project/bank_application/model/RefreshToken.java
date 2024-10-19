package com.springboot_project.bank_application.model;

import static com.springboot_project.bank_application.repo.AccountRepo.CREATED_AT;
import static com.springboot_project.bank_application.repo.AccountRepo.REFRESH_TOKEN;
import static com.springboot_project.bank_application.repo.AccountRepo.UPDATED_AT;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = REFRESH_TOKEN)
public class RefreshToken {

  private String id;

  private String token;

  private String email;

  @Field(CREATED_AT)
  private Instant createdAt;

  @Field(UPDATED_AT)
  private Instant expiresAt;
}
