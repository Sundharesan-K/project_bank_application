package com.springboot_project.bank_application.controller;

import static com.springboot_project.bank_application.constant.Constant.API_ACCOUNT;
import static com.springboot_project.bank_application.constant.Constant.CLICK_OPTION;
import static com.springboot_project.bank_application.constant.Constant.SET_PIN;
import static com.springboot_project.bank_application.constant.Constant.SUCCESS;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.AccountDto;
import com.springboot_project.bank_application.exception.AccountNotFoundException;
import com.springboot_project.bank_application.exception.InvalidPinException;
import com.springboot_project.bank_application.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ACCOUNT)
@RequiredArgsConstructor
@Slf4j
public class AccountController {

  private final AccountService accountService;

  @PostMapping()
  public ResponseEntity<APIResponse> accountCreate(@RequestHeader("Authorization") String auth) {
    APIResponse response = new APIResponse();
    try {
      log.info("Creating account with authorization token.");
      response.setMessage(accountService.accountCreate(auth));
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (AuthenticationException e) {
      log.error("Authentication failed during account creation: {}", e.getMessage());
      response.setErrorMessage("Authentication failed: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    } catch (AccountNotFoundException e) {
      log.error("Account creation failed: Account not found - {}", e.getMessage());
      response.setErrorMessage("Account creation failed: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      log.error("Error during account creation: {}", e.getMessage());
      response.setErrorMessage("Failed to create account: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(SET_PIN)
  public ResponseEntity<APIResponse> pinSet(@Valid @RequestBody AccountDto accountDto) {
    APIResponse response = new APIResponse();
    try {
      log.info("Setting PIN for account: {}", accountDto.getAccountNo());
      response.setMessage(accountService.setPinForAccount(accountDto));
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (InvalidPinException e) {
      log.error("Invalid PIN for account {}: {}", accountDto.getAccountNo(), e.getMessage());
      response.setErrorMessage("Failed to set PIN: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    } catch (AccountNotFoundException e) {
      log.error("Account not found for setting PIN {}: {}", accountDto.getAccountNo(),
          e.getMessage());
      response.setErrorMessage("Account not found: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      log.error("Error setting PIN for account {}: {}", accountDto.getAccountNo(), e.getMessage());
      response.setErrorMessage("Failed to set PIN: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(CLICK_OPTION)
  public ResponseEntity<APIResponse> clickOption(@RequestBody AccountDto accountDto) {
    APIResponse response = new APIResponse();
    try {
      log.info("Processing click options for account: {}", accountDto.getAccountNo());
      response.setData(accountService.clickOptions(accountDto));
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (AccountNotFoundException e) {
      log.error("Click options failed for account {}: {}", accountDto.getAccountNo(),
          e.getMessage());
      response.setErrorMessage("Account not found: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      log.error("Error processing click options for account {}: {}", accountDto.getAccountNo(),
          e.getMessage());
      response.setErrorMessage("Failed to process options: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/updatePIN")
  public ResponseEntity<APIResponse> updatePin(@Valid @RequestBody AccountDto accountDto) {
    APIResponse response = new APIResponse();
    try {
      log.info("Updating PIN for account: {}", accountDto.getAccountNo());
      response.setMessage(accountService.updatePin(accountDto));
      return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (InvalidPinException e) {
      log.error("Invalid PIN for account {}: {}", accountDto.getAccountNo(), e.getMessage());
      response.setErrorMessage("Failed to update PIN: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    } catch (AccountNotFoundException e) {
      log.error("Account not found for updating PIN {}: {}", accountDto.getAccountNo(),
          e.getMessage());
      response.setErrorMessage("Account not found: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      log.error("Error updating PIN for account {}: {}", accountDto.getAccountNo(), e.getMessage());
      response.setErrorMessage("Failed to update PIN: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
