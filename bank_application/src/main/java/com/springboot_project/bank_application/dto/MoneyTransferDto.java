package com.springboot_project.bank_application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class MoneyTransferDto {

  @NotBlank(message = "FromAccount Number is mandatory")
  private String fromAccountNo;

  @NotBlank(message = "ToAccount Number is mandatory")
  private String toAccountNo;

  @NotBlank(message = "Amount is mandatory")
  @DecimalMin(value = "0.99", message = "Amount must be greater than zero")
  private BigDecimal amount;
}
