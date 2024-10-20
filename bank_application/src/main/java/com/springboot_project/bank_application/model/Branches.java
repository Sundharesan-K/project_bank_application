package com.springboot_project.bank_application.model;

import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.BANK_NAME;
import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.BRANCHES;
import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.BRANCH_ID;
import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.BRANCH_NAME;
import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.CREATED_AT;
import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.IFSC_CODE;
import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.SERVICES_OFFERED;
import static com.springboot_project.bank_application.dao.impl.BranchDaoImpl.UPDATED_AT;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = BRANCHES)
public class Branches {
  @Id
  private String id;

  @Field(BANK_NAME)
  private String bankName;

  @Field(BRANCH_ID)
  private String branchId;

  @Field(BRANCH_NAME)
  private String branchName;

  private Location location;

  @Field(IFSC_CODE)
  private String IfscCode;

  @Field(SERVICES_OFFERED)
  private List<String> servicesOffered;

  @Field(CREATED_AT)
  private LocalDateTime createdAt;

  @Field(UPDATED_AT)
  private LocalDateTime updatedAt;
}
