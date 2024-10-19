package com.springboot_project.bank_application.dto;

import com.springboot_project.bank_application.model.Location;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

  @NotBlank(message = "Username is mandatory")
  @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
  private String username;

  @NotBlank(message = "Lastname is mandatory")
  @Size(min = 4, max = 20, message = "Lastname must be between 4 and 20 characters")
  private String lastname;

  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is mandatory")
  private String emailId;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
  private String password;

  @NotNull(message = "Location cannot be null")
  private Location location;
}
