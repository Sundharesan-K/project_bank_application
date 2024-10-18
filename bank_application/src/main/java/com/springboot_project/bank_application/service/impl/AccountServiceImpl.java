package com.springboot_project.bank_application.service.impl;

import static com.springboot_project.bank_application.model.AccountStatusType.ACTIVE;
import static com.springboot_project.bank_application.model.AccountType.SAVINGS;
import static com.springboot_project.bank_application.model.BankOptions.DEPOSIT;
import static com.springboot_project.bank_application.model.BankOptions.WITHDRAW;
import static com.springboot_project.bank_application.util.GenerateRandomNumber.generateRandom12DigitNumber;
import static com.springboot_project.bank_application.util.GenerateRandomNumber.generateTransactionId;

import com.springboot_project.bank_application.dao.BranchRepo;
import com.springboot_project.bank_application.dto.AccountDto;
import com.springboot_project.bank_application.dto.StatementResponse;
import com.springboot_project.bank_application.exception.ActiveException;
import com.springboot_project.bank_application.exception.AccountNotFoundException;
import com.springboot_project.bank_application.exception.IncorrectPinException;
import com.springboot_project.bank_application.model.Account;
import com.springboot_project.bank_application.model.BankOptions;
import com.springboot_project.bank_application.model.Branches;
import com.springboot_project.bank_application.model.Statement;
import com.springboot_project.bank_application.model.Users;
import com.springboot_project.bank_application.repo.AccountRepo;
import com.springboot_project.bank_application.repo.StatementRepo;
import com.springboot_project.bank_application.repo.UserRepo;
import com.springboot_project.bank_application.service.AccountService;
import com.springboot_project.bank_application.service.JWTService;
import com.springboot_project.bank_application.util.MoneyTransactionService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final JWTService jwtService;
  private final UserRepo userRepo;
  private final AccountRepo accountRepo;
  private final PasswordEncoder encoder;
  private final StatementRepo statementRepo;
  private final BranchRepo branchRepo;

  @Override
  public String accountCreate(String auth) {
    auth = auth.substring(7);
    String emailId = jwtService.extractUserName(auth);
    if (ObjectUtils.isEmpty(emailId)) {
      throw new UsernameNotFoundException("Authentication failed. Please try again later.");
    }
    Users user = userRepo.findByEmailId(emailId);
    if (ObjectUtils.isEmpty(user)) {
      throw new UsernameNotFoundException("User not found. Please try again later");
    }
    Account account = accountRepo.findByAccountHolderName(
        user.getUsername() + " " + user.getLastname());
    if (!ObjectUtils.isEmpty(account) && account.getStatus().equals(ACTIVE.name())) {
      throw new ActiveException("Your Account is already active. Please verify your account");
    }
    return createAccountForUser(user);
  }

  private String createAccountForUser(Users user) {
    Account account = new Account();
    account.setAccountHolderName(user.getUsername() + " " + user.getLastname());
    account.setAccountNo(generateRandom12DigitNumber());
    Branches branches = branchRepo.findBranch(user.getLocation().getAddress());
    if (Objects.nonNull(branches)) {
      account.setBankName(branches.getBankName());
      account.setLocation(branches.getLocation());
      account.setBranchId(branches.getBranchId());
      account.setIfscCode(branches.getIfscCode());
    }
    account.setAccountType(SAVINGS.name());
    account.setBankBalance(BigDecimal.ZERO);
    account.setStatus(ACTIVE.name());
    account.setCreatedAt(LocalDateTime.now());
    account.setUpdatedAt(LocalDateTime.now());
    accountRepo.save(account);
    return "Account created Successfully. Please set your secret PIN";
  }

  @Override
  public String setPinForAccount(AccountDto accountDto) {
    Account account = accountRepo.findByAccountNo(accountDto.getAccountNo());
    if (Objects.nonNull(account) && Objects.isNull(account.getSecretPinNo())) {
      account.setSecretPinNo(encoder.encode(accountDto.getPin()));
      accountRepo.save(account);
      return "PIN set Successfully";
    } else {
      throw new AccountNotFoundException("Invalid account number. Please check and try again.");
    }
  }

  @Override
  public Object clickOptions(AccountDto accountDto) {
    Account account = accountRepo.findByAccountNo(accountDto.getAccountNo());
    if (Objects.nonNull(account)) {
      if (encoder.matches(accountDto.getPin(), account.getSecretPinNo())) {
        switch (BankOptions.valueOf(accountDto.getOption())) {
          case WITHDRAW -> {
            BigDecimal amount = MoneyTransactionService.withdrawOrTransferMoney(account.getBankBalance(),
                accountDto.getAmount(), accountDto.getOption());
            account.setBankBalance(amount);
            account.setUpdatedAt(LocalDateTime.now());
            accountRepo.save(account);
            String balance = moneyFormat(account, null);
            buildStatement(accountDto, account);
            return "Money withdrawn successfully. Current balance: " + balance;
          }
          case DEPOSIT -> {
            BigDecimal amount = MoneyTransactionService.depositOrTransferMoney(account.getBankBalance(),
                accountDto.getAmount(), accountDto.getOption());
            account.setBankBalance(amount);
            account.setUpdatedAt(LocalDateTime.now());
            accountRepo.save(account);
            String balance = moneyFormat(account, null);
            buildStatement(accountDto, account);
            return "Money deposited successfully. Current balance: " + balance;
          }
          case BALANCE_ENQUIRY -> {
            String balance = moneyFormat(account, null);
            return "Current Balance : " + balance;
          }
          case STATEMENT -> {
            List<Statement> statements = statementRepo.findByAccountNo(accountDto.getAccountNo());
            if (!statements.isEmpty()) {
              List<StatementResponse> response = new ArrayList<>();
              statements.forEach(statement -> {
                StatementResponse statementResponse = new StatementResponse();
                statementResponse.setType(statement.getType());
                statementResponse.setMessage(statement.getMessage());
                statementResponse.setDateTime(statement.getCreateAt());
                response.add(statementResponse);
              });
              return response;
            } else {
              return "No statements found for this account.";
            }
          }
          default -> {
            return "An error occurred. Please try again later.";
          }
        }
      } else {
        throw new IncorrectPinException("Incorrect PIN. Please try again.");
      }
    } else {
      throw new AccountNotFoundException("Invalid account number. Please check and try again.");
    }
  }

  private void buildStatement(AccountDto accountDto, Account account) {
    Statement statement = new Statement();
    statement.setTransactionId(generateTransactionId());
    statement.setAccountId(account.getId());
    statement.setAccountNo(account.getAccountNo());
    if (accountDto.getOption().equals("WITHDRAW")) {
      statement.setType(WITHDRAW.name());
      statement.setMessage("Withdraw money is : " + moneyFormat(null,
          accountDto.getAmount()));
    } else {
      statement.setType(DEPOSIT.name());
      statement.setMessage("Deposit money is : " + moneyFormat(null,
          accountDto.getAmount()));
    }
    statement.setAmount(accountDto.getAmount().doubleValue());
    statement.setStatus("COMPLETED");
    statement.setCreateAt(LocalDateTime.now());
    statementRepo.save(statement);
  }

  public String moneyFormat(Account account, BigDecimal money) {
    Locale indianLocale = new Locale("en", "IN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(indianLocale);
    if (Objects.nonNull(account)) {
      return currencyFormatter.format(account.getBankBalance());
    } else {
      return currencyFormatter.format(money);
    }
  }
}
