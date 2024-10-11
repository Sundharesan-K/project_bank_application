package com.springboot_project.bank_application.exception;

public class ActiveException extends RuntimeException {

  public ActiveException(String message) {
    super(message);
  }
}
