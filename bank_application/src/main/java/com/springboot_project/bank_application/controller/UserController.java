package com.springboot_project.bank_application.controller;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.UsersDto;
import com.springboot_project.bank_application.model.JwtTokenResponse;
import com.springboot_project.bank_application.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<APIResponse> register(@RequestBody UsersDto usersDto) {
    APIResponse response = new APIResponse();
    try {
      response.setMessage(usersService.registerUser(usersDto));
      response.setStatus("SUCCESS");
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    }catch (Exception e){
      response.setErrorMessage(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/login")
  public JwtTokenResponse login(@RequestBody LoginRequest loginRequest) {
    return usersService.verifyUser(loginRequest);
  }
}
