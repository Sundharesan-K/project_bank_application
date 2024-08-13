package com.springboot_project.bank_application.service;

import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.UsersDto;

public interface UsersService {

  String registerUser(UsersDto usersDto);

  String verifyUser(LoginRequest loginRequest);
}
