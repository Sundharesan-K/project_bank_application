package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends MongoRepository<BankAccount, String> {

  BankAccount findByAccountHolderName(String accountHolderName);

  BankAccount findByAccountNo(String accountNo);
}
