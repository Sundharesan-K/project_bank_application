package com.springboot_project.bank_application.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDto {

  private String accountHolderName;
  private String accountNo;
  private String pin;
  private String option;
  private BigDecimal depositMoney;
  private BigDecimal withdrawMoney;
}
