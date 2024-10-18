package com.springboot_project.bank_application.util;

import java.util.Random;

public class GenerateRandomNumber {

  public static final Random random = new Random();

  public static String generateTransactionId() {
    int randomNumber = 10000 + random.nextInt(90000);
    return "txn" + randomNumber;
  }

  public static String generateRandom12DigitNumber() {
    Random random = new Random();
    long number = Math.abs(random.nextLong() % 1000000000000L);
    return String.format("%012d", number);
  }
}
