package com.springboot_project.bank_application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "bank_account")
public class BankAccount {

  @Id
  private String id;

  @Field("account_holder_name")
  private String accountHolderName;

  @Field("account_number")
  private String accountNo;

  @Field("secret_pin")
  private String secretPinNo;

  @Field("bank_name")
  private String bankOfName;

  @Field("bank_balance")
  private BigDecimal bankBalance;

  @Field("account_status")
  private String accountStatus;

  @Field("create_ts")
  private LocalDateTime createTs;

  @Field("update_ts")
  private LocalDateTime updateTs;
}
