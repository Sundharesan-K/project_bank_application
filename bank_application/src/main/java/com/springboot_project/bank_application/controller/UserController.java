package com.springboot_project.bank_application.controller;

import static com.springboot_project.bank_application.constant.Constant.API_USERS;
import static com.springboot_project.bank_application.constant.Constant.LOGIN;
import static com.springboot_project.bank_application.constant.Constant.REFRESH_TOKEN;
import static com.springboot_project.bank_application.constant.Constant.REGISTER;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.RefreshTokenRequest;
import com.springboot_project.bank_application.dto.UsersDto;
import com.springboot_project.bank_application.service.RefreshTokenService;
import com.springboot_project.bank_application.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_USERS)
@RequiredArgsConstructor
public class UserController {

  private final UsersService usersService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping(REGISTER)
  public ResponseEntity<APIResponse> register(@Valid @RequestBody UsersDto usersDto) {
    APIResponse response = new APIResponse();
    try {
      response.setMessage(usersService.registerUser(usersDto));
      response.setStatus("SUCCESS");
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      response.setErrorMessage(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(LOGIN)
  public ResponseEntity<APIResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    APIResponse response = new APIResponse();
    try {
      response.setData(usersService.verifyUser(loginRequest));
      response.setStatus(HttpStatus.OK.toString());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      response.setErrorMessage(e.getMessage());
      response.setStatus(HttpStatus.BAD_REQUEST.name());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(REFRESH_TOKEN)
  public ResponseEntity<APIResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest tokenRequest) {
    APIResponse response = new APIResponse();
    try {
      response.setData(refreshTokenService.refreshToken(tokenRequest));
      response.setStatus(HttpStatus.OK.toString());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      response.setErrorMessage(e.getMessage());
      response.setStatus(HttpStatus.NOT_FOUND.toString());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }
}
