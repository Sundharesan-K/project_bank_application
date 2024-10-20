package com.springboot_project.bank_application.exception;

public class InvalidCredentialsException extends RuntimeException{

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
