package com.springboot_project.bank_application.exception;

public class RefreshTokenException extends RuntimeException {

  public RefreshTokenException(String message) {
    super(message);
  }
}
