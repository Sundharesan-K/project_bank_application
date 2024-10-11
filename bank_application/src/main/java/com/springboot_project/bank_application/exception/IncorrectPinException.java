package com.springboot_project.bank_application.exception;

public class IncorrectPinException extends RuntimeException {

  public IncorrectPinException(String message) {
    super(message);
  }
}
