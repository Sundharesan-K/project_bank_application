package com.springboot_project.bank_application.model;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

  private String authenticationToken;
  private String refreshToken;
  private Instant expiresAt;
  private String email;
}
