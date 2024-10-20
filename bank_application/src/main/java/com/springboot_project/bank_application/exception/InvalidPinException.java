package com.springboot_project.bank_application.exception;

public class InvalidPinException extends RuntimeException {

  public InvalidPinException(String message) {
    super(message);
  }

}
