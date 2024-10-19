package com.springboot_project.bank_application.model;

import static com.springboot_project.bank_application.repo.AccountRepo.ACCOUNTS;
import static com.springboot_project.bank_application.repo.AccountRepo.ACCOUNT_HOLDER_NAME;
import static com.springboot_project.bank_application.repo.AccountRepo.ACCOUNT_NUMBER;
import static com.springboot_project.bank_application.repo.AccountRepo.ACCOUNT_TYPE;
import static com.springboot_project.bank_application.repo.AccountRepo.BANK_BALANCE;
import static com.springboot_project.bank_application.repo.AccountRepo.BANK_NAME;
import static com.springboot_project.bank_application.repo.AccountRepo.BRANCH_ID;
import static com.springboot_project.bank_application.repo.AccountRepo.CREATED_AT;
import static com.springboot_project.bank_application.repo.AccountRepo.IFSC_CODE;
import static com.springboot_project.bank_application.repo.AccountRepo.SECRET_PIN;
import static com.springboot_project.bank_application.repo.AccountRepo.STATUS;
import static com.springboot_project.bank_application.repo.AccountRepo.UPDATED_AT;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = ACCOUNTS)
public class Account {

  @Id
  private String id;

  @Field(ACCOUNT_HOLDER_NAME)
  private String accountHolderName;

  @Field(ACCOUNT_NUMBER)
  private String accountNo;

  @Field(SECRET_PIN)
  private String secretPinNo;

  @Field(BANK_NAME)
  private String bankName;

  private Location location;

  @Field(BRANCH_ID)
  private String branchId;

  @Field(IFSC_CODE)
  private String IfscCode;

  @Field(ACCOUNT_TYPE)
  private String accountType;

  @Field(BANK_BALANCE)
  private BigDecimal bankBalance;

  @Field(STATUS)
  private String status;

  @Field(CREATED_AT)
  private LocalDateTime createdAt;

  @Field(UPDATED_AT)
  private LocalDateTime updatedAt;
}
