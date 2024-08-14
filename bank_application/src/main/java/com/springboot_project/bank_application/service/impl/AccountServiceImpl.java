package com.springboot_project.bank_application.service.impl;

import static com.springboot_project.bank_application.model.AccountStatusType.ACTIVE;

import com.springboot_project.bank_application.exception.ActiveException;
import com.springboot_project.bank_application.model.BankAccount;
import com.springboot_project.bank_application.model.Users;
import com.springboot_project.bank_application.repo.AccountRepo;
import com.springboot_project.bank_application.repo.UserRepo;
import com.springboot_project.bank_application.service.AccountService;
import com.springboot_project.bank_application.service.JWTService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final JWTService jwtService;
  private final UserRepo userRepo;
  private final AccountRepo accountRepo;

  @Override
  public String accountCreate(String auth) {
    auth = auth.substring(7);
    String emailId = jwtService.extractUserName(auth);
    if (ObjectUtils.isEmpty(emailId)) {
      throw new UsernameNotFoundException("Please try again later");
    }
    Users user = userRepo.findByEmailId(emailId);
    if (ObjectUtils.isEmpty(user)) {
      throw new UsernameNotFoundException("Please try again later");
    }
    BankAccount bankAccount = accountRepo.findByAccountHolderName(
        user.getUsername() + " " + user.getLastname());
    if (!ObjectUtils.isEmpty(bankAccount) && bankAccount.getAccountStatus().equals(ACTIVE.name())) {
      throw new ActiveException("Your Account already activated.So kindly verify your account");
    }
    return createBankAccountForUser(user);
  }

  private String createBankAccountForUser(Users user) {
    BankAccount bankAccount = new BankAccount();
    bankAccount.setAccountHolderName(user.getUsername() + " " + user.getLastname());
    bankAccount.setAccountNo(generateRandom12DigitNumber());
    bankAccount.setBankOfName("INDIAN BANK");
    bankAccount.setBankBalance(BigDecimal.ZERO);
    bankAccount.setAccountStatus(ACTIVE.name());
    bankAccount.setCreateTs(LocalDateTime.now());
    bankAccount.setUpdateTs(LocalDateTime.now());
    accountRepo.save(bankAccount);
    return "Account created Successfully. Can you please set secret pin";
  }

  private String generateRandom12DigitNumber() {
    Random random = new Random();
    long number = Math.abs(random.nextLong() % 1000000000000L);
    return String.format("%012d", number);
  }
}
