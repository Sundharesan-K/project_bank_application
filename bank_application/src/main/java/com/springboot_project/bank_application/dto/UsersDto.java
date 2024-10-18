package com.springboot_project.bank_application.dto;

import com.springboot_project.bank_application.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

  private String username;
  private String lastname;
  private String emailId;
  private String password;
  private Location location;
}
