package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.Account;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends MongoRepository<Account, String> {

  String ACCOUNTS = "accounts";
  String REFRESH_TOKEN = "refresh_token";
  String ACCOUNT_HOLDER_NAME = "account_holder_name";
  String ACCOUNT_NUMBER = "account_number";
  String SECRET_PIN = "secret_pin";
  String BANK_NAME = "bank_name";
  String BRANCH_ID = "branch_id";
  String IFSC_CODE = "IFSC_code";
  String ACCOUNT_TYPE = "account_type";
  String BANK_BALANCE = "bank_balance";
  String STATUS = "status";
  String CREATED_AT = "created_at";
  String UPDATED_AT = "updated_at";
  String ADDRESS = "address";
  String CITY = "city";
  String STATE = "state";
  String COUNTRY = "country";

  Account findByAccountHolderName(String accountHolderName);

  Account findByAccountNo(String accountNo);
}
