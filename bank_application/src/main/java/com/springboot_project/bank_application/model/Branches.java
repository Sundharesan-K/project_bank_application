package com.springboot_project.bank_application.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "branches")
public class Branches {
  @Id
  private String id;

  @Field("bank_name")
  private String bankName;

  @Field("branch_id")
  private String branchId;

  @Field("branch_name")
  private String branchName;

  private Location location;

  @Field("IFSC_code")
  private String IfscCode;

  @Field("services_offered")
  private List<String> servicesOffered;

  @Field("created_at")
  private LocalDateTime createdAt;

  @Field("updated_at")
  private LocalDateTime updatedAt;
}
