package com.springboot_project.bank_application.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MoneyTransferDto {

  private String fromAccountNo;
  private String toAccountNo;
  private BigDecimal amount;
}
