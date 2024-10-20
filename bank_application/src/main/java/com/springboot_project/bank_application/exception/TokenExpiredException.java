package com.springboot_project.bank_application.exception;

public class TokenExpiredException extends RuntimeException{

  public TokenExpiredException(String message){
    super(message);
  }
}
