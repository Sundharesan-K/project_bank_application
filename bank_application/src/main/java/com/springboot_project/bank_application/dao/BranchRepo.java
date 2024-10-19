package com.springboot_project.bank_application.dao;

import com.springboot_project.bank_application.model.Branches;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BranchRepo {

  public static final String BRANCHES = "branches";
  public static final String BANK_NAME = "bank_name";
  public static final String BRANCH_ID = "branch_id";
  public static final String BRANCH_NAME = "branch_name";
  public static final String IFSC_CODE = "IFSC_code";
  public static final String SERVICES_OFFERED = "services_offered";
  public static final String CREATED_AT = "created_at";
  public static final String UPDATED_AT = "updated_at";

  private final MongoTemplate mongoTemplate;

  public Branches findBranch(String address) {
    Query query = new Query();
    query.addCriteria(Criteria.where("location.address").is(address));
    return mongoTemplate.findOne(query, Branches.class);
  }
}
