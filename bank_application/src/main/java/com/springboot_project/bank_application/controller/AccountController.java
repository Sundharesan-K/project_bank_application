package com.springboot_project.bank_application.controller;

import static com.springboot_project.bank_application.constant.Constant.API_ACCOUNT;
import static com.springboot_project.bank_application.constant.Constant.CLICK_OPTION;
import static com.springboot_project.bank_application.constant.Constant.SET_PIN;
import static com.springboot_project.bank_application.constant.Constant.SUCCESS;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.AccountDto;
import com.springboot_project.bank_application.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ACCOUNT)
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @PostMapping()
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

  @PostMapping(SET_PIN)
  public ResponseEntity<APIResponse> pinSet(@Valid @RequestBody AccountDto accountDto) {
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

  @PostMapping(CLICK_OPTION)
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
