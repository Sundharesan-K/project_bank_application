package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends MongoRepository<Transaction, String> {

  String TRANSACTIONS = "transactions";
  String FROM_ACCOUNT_NO = "from_account_no";
  String TO_ACCOUNT_NO = "to_account_no";
  String TRANSACTION_TYPE = "transaction_type";
  String CREATED_AT = "created_at";

}
