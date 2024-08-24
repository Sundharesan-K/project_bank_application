package com.springboot_project.bank_application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class APIResponse {

  private Object data;
  private String status;
  private String message;
  private String errorMessage;
}
