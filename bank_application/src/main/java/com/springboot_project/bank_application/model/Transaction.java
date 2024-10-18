package com.springboot_project.bank_application.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "transactions")
public class Transaction {

  @Id
  private String id;

  @Field("from_account_no")
  private String fromAccountNo;

  @Field("to_account_no")
  private String toAccountNo;

  private double amount;

  @Field("transaction_type")
  private String transactionType;

  private String status;

  @Field("created_at")
  private LocalDateTime createdAt;
}
