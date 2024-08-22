package com.springboot_project.bank_application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenResponse {

  private String token;
  private String emailId;
}
