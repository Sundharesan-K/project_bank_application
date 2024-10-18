package com.springboot_project.bank_application.service;

import com.springboot_project.bank_application.dto.MoneyTransferDto;

public interface MoneyTransferService {

  String transferMoney(MoneyTransferDto moneyTransferDto);
}
