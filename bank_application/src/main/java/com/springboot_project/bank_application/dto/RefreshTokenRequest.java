package com.springboot_project.bank_application.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {

  private String refreshToken;
  private String email;
}
