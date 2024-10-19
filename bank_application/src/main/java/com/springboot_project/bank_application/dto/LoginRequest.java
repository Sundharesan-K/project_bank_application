package com.springboot_project.bank_application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is mandatory")
  private String emailId;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
  private String password;
}
