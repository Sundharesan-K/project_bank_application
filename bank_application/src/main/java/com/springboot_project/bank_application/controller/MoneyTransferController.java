package com.springboot_project.bank_application.controller;

import static com.springboot_project.bank_application.constant.Constant.API_TRANSFER;
import static com.springboot_project.bank_application.constant.Constant.SUCCESS;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.MoneyTransferDto;
import com.springboot_project.bank_application.service.MoneyTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_TRANSFER)
public class MoneyTransferController {

  private final MoneyTransferService moneyTransferService;

  @PostMapping
  public ResponseEntity<APIResponse> moneyTransfer(@Valid @RequestBody MoneyTransferDto moneyTransferDto) {
    APIResponse response = new APIResponse();
    try {
      String message = moneyTransferService.transferMoney(moneyTransferDto);
      response.setMessage(message);
      response.setStatus(SUCCESS);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e){
      response.setErrorMessage(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }
}
