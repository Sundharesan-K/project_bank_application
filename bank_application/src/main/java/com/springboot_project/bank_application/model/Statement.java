package com.springboot_project.bank_application.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "statement")
public class Statement {

  @Id
  private String id;
  private String accountNo;
  private String type;
  private String message;
}
