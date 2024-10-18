package com.springboot_project.bank_application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "accounts")
public class Account {

  @Id
  private String id;

  @Field("account_holder_name")
  private String accountHolderName;

  @Field("account_number")
  private String accountNo;

  @Field("secret_pin")
  private String secretPinNo;

  @Field("bank_name")
  private String bankName;

  private Location location;

  @Field("branch_id")
  private String branchId;

  @Field("IFSC_code")
  private String IfscCode;

  @Field("account_type")
  private String accountType;

  @Field("bank_balance")
  private BigDecimal bankBalance;

  @Field("status")
  private String status;

  @Field("created_at")
  private LocalDateTime createdAt;

  @Field("updated_at")
  private LocalDateTime updatedAt;
}
