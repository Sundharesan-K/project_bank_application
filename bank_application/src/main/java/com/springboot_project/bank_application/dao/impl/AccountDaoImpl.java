package com.springboot_project.bank_application.dao.impl;

import static com.springboot_project.bank_application.constant.Constant.PIN_SET_SUCCESS;
import static com.springboot_project.bank_application.repo.AccountRepo.ACCOUNT_NUMBER;
import static com.springboot_project.bank_application.repo.AccountRepo.SECRET_PIN;

import com.springboot_project.bank_application.dao.AccountDao;
import com.springboot_project.bank_application.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountDaoImpl implements AccountDao {

  private final MongoTemplate template;

  @Override
  public String updatePin(Account account, String newPin) throws Exception {
    Query query = new Query();
    try {
      query.addCriteria(Criteria.where(ACCOUNT_NUMBER).is(account.getAccountNo()));
      Update update = new Update();
      update.set(SECRET_PIN, newPin);
      template.updateFirst(query, update, Account.class);
      log.info("PIN updated successfully for account number:  {}", account.getAccountNo());
      return PIN_SET_SUCCESS;
    } catch (Exception e) {
      log.error("Error updating PIN: {}", e.getMessage());
      throw new Exception(e.getMessage());
    }
  }
}
