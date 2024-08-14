package com.springboot_project.bank_application.util;

import java.math.BigDecimal;

public class MoneyTransactionService {

  public static BigDecimal deposit(BigDecimal currentBalance, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Deposit amount must be greater than zero");
    }
    return currentBalance.add(amount);
  }

  public static BigDecimal withdraw(BigDecimal currentBalance, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
    }
    if (currentBalance.compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient balance");
    }
    return currentBalance.subtract(amount);
  }
}
