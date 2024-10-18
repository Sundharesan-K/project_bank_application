package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends MongoRepository<Transaction, String> {

}
