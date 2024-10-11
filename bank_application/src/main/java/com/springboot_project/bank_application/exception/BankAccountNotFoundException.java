package com.springboot_project.bank_application.exception;

public class BankAccountNotFoundException extends RuntimeException {

  public BankAccountNotFoundException(String message) {
    super(message);
  }
}
