package com.springboot_project.bank_application.dto;

import lombok.Data;

@Data
public class LoginRequest {
  private String emailId;
  private String password;
}
