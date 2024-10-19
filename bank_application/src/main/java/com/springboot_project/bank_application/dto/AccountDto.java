package com.springboot_project.bank_application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDto {

  @NotBlank(message = "Account Number is mandatory")
  private String accountNo;

  @NotBlank(message = "PIN is mandatory")
  @Size(min = 4, max = 10, message = "PIN must be between 4 and 10 numbers")
//  @Pattern(regexp = "//d+", message = "PIN must contain only numbers")
  private String pin;

  private String option;

  private BigDecimal amount;
}
