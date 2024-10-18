package com.springboot_project.bank_application.exception;

public class FieldNotFoundException extends RuntimeException{

  public FieldNotFoundException(String message) {
    super(message);
  }
}
