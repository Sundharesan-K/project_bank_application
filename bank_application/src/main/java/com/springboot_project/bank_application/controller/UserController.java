package com.springboot_project.bank_application.controller;

import static com.springboot_project.bank_application.constant.Constant.API_USERS;
import static com.springboot_project.bank_application.constant.Constant.LOGIN;
import static com.springboot_project.bank_application.constant.Constant.REFRESH_TOKEN;
import static com.springboot_project.bank_application.constant.Constant.REGISTER;
import static com.springboot_project.bank_application.constant.Constant.SUCCESS;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.RefreshTokenRequest;
import com.springboot_project.bank_application.dto.UsersDto;
import com.springboot_project.bank_application.exception.InvalidCredentialsException;
import com.springboot_project.bank_application.exception.TokenExpiredException;
import com.springboot_project.bank_application.exception.UserNotFoundException;
import com.springboot_project.bank_application.service.RefreshTokenService;
import com.springboot_project.bank_application.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {

  private final UsersService usersService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping(REGISTER)
  public ResponseEntity<APIResponse> register(@Valid @RequestBody UsersDto usersDto) {
    APIResponse response = new APIResponse();
    try {
      log.info("Registering user: {}", usersDto.getEmailId());
      response.setMessage(usersService.registerUser(usersDto));
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (Exception e) {
      log.error("Error during user registration: {}", e.getMessage());
      response.setErrorMessage("Failed to register user: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(LOGIN)
  public ResponseEntity<APIResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    APIResponse response = new APIResponse();
    try {
      log.info("User login attempt: {}", loginRequest.getEmailId());
      response.setData(usersService.verifyUser(loginRequest));
      response.setStatus(HttpStatus.OK.toString());
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (InvalidCredentialsException e) {
      log.error("Invalid login credentials for user: {}", loginRequest.getEmailId());
      response.setErrorMessage("Invalid credentials: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    } catch (UserNotFoundException e) {
      log.error("User not found during login attempt: {}", loginRequest.getEmailId());
      response.setErrorMessage("User not found: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      log.error("Error during user login: {}", e.getMessage());
      response.setErrorMessage("Login failed: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(REFRESH_TOKEN)
  public ResponseEntity<APIResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest tokenRequest) {
    APIResponse response = new APIResponse();
    try {
      log.info("Refreshing token for user: {}", tokenRequest.getEmail());
      response.setData(refreshTokenService.refreshToken(tokenRequest));
      response.setStatus(HttpStatus.OK.toString());
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (TokenExpiredException e) {
      log.error("Token expired for user: {}", tokenRequest.getEmail());
      response.setErrorMessage("Token expired: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    } catch (UserNotFoundException e) {
      log.error("User not found during token refresh: {}", tokenRequest.getEmail());
      response.setErrorMessage("User not found: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      log.error("Error during token refresh: {}", e.getMessage());
      response.setErrorMessage("Token refresh failed: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
