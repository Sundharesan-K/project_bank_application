package com.springboot_project.bank_application.controller;

import com.springboot_project.bank_application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  public String accountCreate(@RequestHeader("Authorization") String auth){
    return accountService.accountCreate(auth);
  }
}
