package com.springboot_project.bank_application.controller;

import com.springboot_project.bank_application.dto.APIResponse;
import com.springboot_project.bank_application.dto.MoneyTransferDto;
import com.springboot_project.bank_application.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfer")
public class MoneyTransferController {

  private final MoneyTransferService moneyTransferService;
  private static final String SUCCESS = "Success";

  @PostMapping
  public ResponseEntity<APIResponse> moneyTransfer(@RequestBody MoneyTransferDto moneyTransferDto) {
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
