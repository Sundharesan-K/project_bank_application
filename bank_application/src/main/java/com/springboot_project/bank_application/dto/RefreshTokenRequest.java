package com.springboot_project.bank_application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

  @NotBlank(message = "Refresh Token is mandatory")
  private String refreshToken;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;
}
