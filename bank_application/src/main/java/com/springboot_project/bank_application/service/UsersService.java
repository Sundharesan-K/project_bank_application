package com.springboot_project.bank_application.service;

import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.UsersDto;
import com.springboot_project.bank_application.model.AuthenticationResponse;

public interface UsersService {

  String registerUser(UsersDto usersDto);

  AuthenticationResponse verifyUser(LoginRequest loginRequest);
}
