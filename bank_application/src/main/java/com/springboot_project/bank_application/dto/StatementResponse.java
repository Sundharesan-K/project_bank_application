package com.springboot_project.bank_application.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StatementResponse {

  private String type;
  private String message;
  private LocalDateTime dateTime;
}
