package com.springboot_project.bank_application.dao;

import com.springboot_project.bank_application.model.Account;

public interface AccountDao {

  String updatePin(Account account, String newPin) throws Exception;
}
