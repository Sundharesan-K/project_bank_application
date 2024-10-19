package com.springboot_project.bank_application.constant;

public class Constant {
  // end points
  public static final String API_ACCOUNT = "/api/account";
  public static final String SET_PIN = "/set-pin";
  public static final String CLICK_OPTION = "/click-option";
  public static final String API_TRANSFER = "/api/transfer";
  public static final String API_USERS = "/api/users";
  public static final String REGISTER = "/register";
  public static final String LOGIN = "/login";
  public static final String REFRESH_TOKEN = "/refreshToken";

  public static final String CURRENT_BALANCE = "Current Balance : ";

  //Set messages
  public static final String COMPLETED = "COMPLETED";
  public static final String WITHDRAW_MONEY = "Withdraw money is : ";
  public static final String DEPOSIT_MONEY = "Deposit money is : ";

  //Success message
  public static final String SUCCESS = "Success";
  public static final String ACCOUNT_CREATED_SUCCESS = "Account created Successfully. Please set your secret PIN";
  public static final String PIN_SET_SUCCESS = "PIN set Successfully";
  public static final String ALREADY_IN_PIN = "You already have a PIN. If you've forgotten it, please set a new PIN.";
  public static final String MONEY_WITHDRAW_SUCCESS = "Money withdrawn successfully. Current balance: ";
  public static final String MONEY_DEPOSIT_SUCCESS = "Money deposited successfully. Current balance: ";
  public static final String NO_STATEMENT_FOUND = "No statements found for this account.";
  public static final String MONEY_TRANSFER_SUCCESS = "Money Transfer Successfully";
  public static final String USER_REGISTER_SUCCESS = "User registered successfully with email ID: ";


  //Error message
  public static final String AUTHENTICATION_FAILED = "Authentication failed. Please try again later.";
  public static final String USER_NOT_FOUND = "User not found. Please try again later";
  public static final String YOUR_ACCOUNT_ALREADY_ACTIVE = "Your Account is already active. Please verify your account";
  public static final String INVALID_ACCOUNT = "Invalid account number. Please check and try again.";
  public static final String ERROR_OCCURRED = "An error occurred. Please try again later.";
  public static final String INCORRECT_PIN = "Incorrect PIN. Please try again.";
  public static final String INVALID_REFRESH_TOKEN = "Invalid Refresh token";
  public static final String INSUFFICIENT_BALANCE = "Insufficient balance";
}
