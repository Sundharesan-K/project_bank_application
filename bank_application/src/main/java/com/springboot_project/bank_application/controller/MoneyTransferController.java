package com.springboot_project.bank_application.controller;

import static com.springboot_project.bank_application.constant.Constant.API_TRANSFER;
import static com.springboot_project.bank_application.constant.Constant.SUCCESS;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.MoneyTransferDto;
import com.springboot_project.bank_application.exception.AccountNotFoundException;
import com.springboot_project.bank_application.exception.InsufficientBalanceException;
import com.springboot_project.bank_application.service.MoneyTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_TRANSFER)
@Slf4j
public class MoneyTransferController {

  private final MoneyTransferService moneyTransferService;

  @PostMapping
  public ResponseEntity<APIResponse> moneyTransfer(
      @Valid @RequestBody MoneyTransferDto moneyTransferDto) {
    APIResponse response = new APIResponse();
    try {
      log.info("Initiating money transfer from account: {} to account: {}",
          moneyTransferDto.getFromAccountNo(), moneyTransferDto.getToAccountNo());

      String message = moneyTransferService.transferMoney(moneyTransferDto);
      response.setMessage(message);
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (InsufficientBalanceException e) {
      log.error("Insufficient balance for account: {}", moneyTransferDto.getFromAccountNo(), e);
      response.setErrorMessage("Transfer failed due to insufficient balance: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    } catch (AccountNotFoundException e) {
      log.error("Account not found: {}", e.getMessage());
      response.setErrorMessage("Transfer failed: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      log.error("Error during money transfer from account: {}",
          moneyTransferDto.getFromAccountNo(), e);
      response.setErrorMessage("Transfer failed due to an internal error: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
