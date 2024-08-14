package com.springboot_project.bank_application.controller;

import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.UsersDto;
import com.springboot_project.bank_application.model.JwtTokenResponse;
import com.springboot_project.bank_application.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UsersService usersService;

  @PostMapping("/register")
  public String register(@RequestBody UsersDto usersDto) {
    return usersService.registerUser(usersDto);
  }

  @PostMapping("/login")
  public JwtTokenResponse login(@RequestBody LoginRequest loginRequest) {
    return usersService.verifyUser(loginRequest);
  }
}
