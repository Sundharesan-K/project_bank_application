package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends MongoRepository<Account, String> {

  Account findByAccountHolderName(String accountHolderName);

  Account findByAccountNo(String accountNo);
}
