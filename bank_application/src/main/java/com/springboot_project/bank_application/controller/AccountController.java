package com.springboot_project.bank_application.controller;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.AccountDto;
import com.springboot_project.bank_application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;
  private static final String SUCCESS = "Success";

  @PostMapping("/")
  public ResponseEntity<APIResponse> accountCreate(@RequestHeader("Authorization") String auth) {
    APIResponse response = new APIResponse();
    try {
      response.setMessage(accountService.accountCreate(auth));
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      response.setErrorMessage(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/set-pin")
  public ResponseEntity<APIResponse> pinSet(@RequestBody AccountDto accountDto) {
    APIResponse response = new APIResponse();
    try {
      response.setMessage(accountService.setPinForAccount(accountDto));
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      response.setErrorMessage(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/click-option")
  public ResponseEntity<APIResponse> clickOption(@RequestBody AccountDto accountDto) {
    APIResponse response = new APIResponse();
    try {
      response.setData(accountService.clickOptions(accountDto));
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      response.setErrorMessage(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }
}
