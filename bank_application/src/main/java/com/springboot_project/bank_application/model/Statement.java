package com.springboot_project.bank_application.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "statement")
public class Statement {

  @Id
  private String id;
  @Field("transaction_id")
  private String transactionId;
  @Field("account_id")
  private String accountId;
  @Field("account_no")
  private String accountNo;
  private String type;
  private Double amount;
  private String status;
  private String message;
  @Field("created_at")
  private LocalDateTime createAt;
}
