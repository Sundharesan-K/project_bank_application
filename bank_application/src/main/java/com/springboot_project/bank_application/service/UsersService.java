package com.springboot_project.bank_application.service;

import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.UsersDto;
import com.springboot_project.bank_application.model.JwtTokenResponse;

public interface UsersService {

  String registerUser(UsersDto usersDto);

  JwtTokenResponse verifyUser(LoginRequest loginRequest);
}
