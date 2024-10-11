package com.springboot_project.bank_application.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "refresh_token")
public class RefreshToken {

  private String id;
  private String token;
  private String email;
  private Instant createdAt;
  private Instant expiresAt;
}
