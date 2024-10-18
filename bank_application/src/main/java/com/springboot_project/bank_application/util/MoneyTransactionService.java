package com.springboot_project.bank_application.util;

import static com.springboot_project.bank_application.model.BankOptions.DEPOSIT;
import static com.springboot_project.bank_application.model.BankOptions.WITHDRAW;

import java.math.BigDecimal;

public class MoneyTransactionService {

  public static BigDecimal depositOrTransferMoney(BigDecimal currentBalance, BigDecimal amount,
      String transactionType) {
    if (transactionType.equals(DEPOSIT.name())) {
      if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Deposit amount must be greater than zero");
      }
    } else {
      if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Transfer amount must be greater than zero");
      }
    }
    return currentBalance.add(amount);
  }

  public static BigDecimal withdrawOrTransferMoney(BigDecimal currentBalance, BigDecimal amount,
      String transactionType) {
    if (transactionType.equals(WITHDRAW.name())) {
      if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
      }
      if (currentBalance.compareTo(amount) < 0) {
        throw new IllegalArgumentException("Insufficient balance");
      }
    } else {
      if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Transfer amount must be greater than zero");
      }
      if (currentBalance.compareTo(amount) < 0) {
        throw new IllegalArgumentException("Insufficient balance");
      }
    }
    return currentBalance.subtract(amount);
  }
}
