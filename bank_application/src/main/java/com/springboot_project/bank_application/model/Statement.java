package com.springboot_project.bank_application.model;

import static com.springboot_project.bank_application.repo.StatementRepo.ACCOUNT_ID;
import static com.springboot_project.bank_application.repo.StatementRepo.ACCOUNT_NO;
import static com.springboot_project.bank_application.repo.StatementRepo.CREATED_AT;
import static com.springboot_project.bank_application.repo.StatementRepo.STATEMENT;
import static com.springboot_project.bank_application.repo.StatementRepo.TRANSACTION_ID;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = STATEMENT)
public class Statement {

  @Id
  private String id;

  @Field(TRANSACTION_ID)
  private String transactionId;

  @Field(ACCOUNT_ID)
  private String accountId;

  @Field(ACCOUNT_NO)
  private String accountNo;

  private String type;
  private Double amount;
  private String status;
  private String message;

  @Field(CREATED_AT)
  private LocalDateTime createAt;
}
