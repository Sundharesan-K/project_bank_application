package com.springboot_project.bank_application.model;

import static com.springboot_project.bank_application.repo.TransactionRepo.CREATED_AT;
import static com.springboot_project.bank_application.repo.TransactionRepo.FROM_ACCOUNT_NO;
import static com.springboot_project.bank_application.repo.TransactionRepo.TO_ACCOUNT_NO;
import static com.springboot_project.bank_application.repo.TransactionRepo.TRANSACTIONS;
import static com.springboot_project.bank_application.repo.TransactionRepo.TRANSACTION_TYPE;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = TRANSACTIONS)
public class Transaction {

  @Id
  private String id;

  @Field(FROM_ACCOUNT_NO)
  private String fromAccountNo;

  @Field(TO_ACCOUNT_NO)
  private String toAccountNo;

  private double amount;

  @Field(TRANSACTION_TYPE)
  private String transactionType;

  private String status;

  @Field(CREATED_AT)
  private LocalDateTime createdAt;
}
