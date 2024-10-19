package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Statement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepo extends MongoRepository<Statement, String> {

  String STATEMENT = "statement";
  String TRANSACTION_ID = "transaction_id";
  String ACCOUNT_ID = "account_id";
  String ACCOUNT_NO = "account_no";
  String CREATED_AT = "created_at";

  List<Statement> findByAccountNo(String accountNo);
}
