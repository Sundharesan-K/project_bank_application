package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Statement;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepo extends MongoRepository<Statement, String> {

  List<Statement> findByAccountNo(String accountNo);
}
