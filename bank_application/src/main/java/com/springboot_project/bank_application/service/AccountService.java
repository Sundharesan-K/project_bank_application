package com.springboot_project.bank_application.service;

import com.springboot_project.bank_application.dto.AccountDto;

public interface AccountService {

  String accountCreate(String auth);

  String setPinForAccount(AccountDto accountDto);

  String deposit(AccountDto accountDto);

  String withdraw(AccountDto accountDto);
}
