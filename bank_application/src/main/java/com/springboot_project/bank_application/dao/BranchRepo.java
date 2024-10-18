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

  private final MongoTemplate mongoTemplate;

  public Branches findBranch(String address) {
    Query query = new Query();
    query.addCriteria(Criteria.where("location.address").is(address));
    return mongoTemplate.findOne(query, Branches.class);
  }
}
