package com.springboot_project.bank_application.service.impl;

import static com.springboot_project.bank_application.model.AccountStatusType.ACTIVE;
import static com.springboot_project.bank_application.model.BankOptions.DEPOSIT;
import static com.springboot_project.bank_application.model.BankOptions.WITHDRAW;

import com.springboot_project.bank_application.dto.AccountDto;
import com.springboot_project.bank_application.dto.StatementResponse;
import com.springboot_project.bank_application.exception.ActiveException;
import com.springboot_project.bank_application.exception.BankAccountNotFoundException;
import com.springboot_project.bank_application.exception.IncorrectPinException;
import com.springboot_project.bank_application.model.BankAccount;
import com.springboot_project.bank_application.model.BankOptions;
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
import java.util.Random;
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
    BankAccount bankAccount = accountRepo.findByAccountHolderName(
        user.getUsername() + " " + user.getLastname());
    if (!ObjectUtils.isEmpty(bankAccount) && bankAccount.getAccountStatus().equals(ACTIVE.name())) {
      throw new ActiveException("Your Account is already active. Please verify your account");
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
    return "Account created Successfully. Please set your secret PIN";
  }

  private String generateRandom12DigitNumber() {
    Random random = new Random();
    long number = Math.abs(random.nextLong() % 1000000000000L);
    return String.format("%012d", number);
  }

  @Override
  public String setPinForAccount(AccountDto accountDto) {
    BankAccount bankAccount = accountRepo.findByAccountNo(accountDto.getAccountNo());
    if (Objects.nonNull(bankAccount) && Objects.isNull(bankAccount.getSecretPinNo())) {
      bankAccount.setSecretPinNo(encoder.encode(accountDto.getPin()));
      accountRepo.save(bankAccount);
      return "PIN set Successfully";
    } else {
      throw new BankAccountNotFoundException("Invalid account number. Please check and try again.");
    }
  }

  @Override
  public Object clickOptions(AccountDto accountDto) {
    BankAccount bankAccount = accountRepo.findByAccountNo(accountDto.getAccountNo());
    if (Objects.nonNull(bankAccount)) {
      if (encoder.matches(accountDto.getPin(), bankAccount.getSecretPinNo())) {
        switch (BankOptions.valueOf(accountDto.getOption())) {
          case WITHDRAW -> {
            BigDecimal amount = MoneyTransactionService.withdraw(bankAccount.getBankBalance(),
                accountDto.getWithdrawMoney());
            bankAccount.setBankBalance(amount);
            accountRepo.save(bankAccount);
            String balance = moneyFormat(bankAccount, null);
            Statement statement = new Statement();
            statement.setAccountNo(bankAccount.getAccountNo());
            statement.setType(WITHDRAW.name());
            statement.setMessage("Withdraw money is : " + moneyFormat(null,
                accountDto.getWithdrawMoney()));
            statement.setDateTime(LocalDateTime.now());
            statementRepo.save(statement);
            return "Money withdrawn successfully. Current balance: " + balance;
          }
          case DEPOSIT -> {
            BigDecimal amount = MoneyTransactionService.deposit(bankAccount.getBankBalance(),
                accountDto.getDepositMoney());
            bankAccount.setBankBalance(amount);
            accountRepo.save(bankAccount);
            String balance = moneyFormat(bankAccount, null);
            Statement statement = new Statement();
            statement.setAccountNo(bankAccount.getAccountNo());
            statement.setType(DEPOSIT.name());
            statement.setMessage(
                "Deposit money is : " + moneyFormat(null, accountDto.getDepositMoney()));
            statement.setDateTime(LocalDateTime.now());
            statementRepo.save(statement);
            return "Money deposited successfully. Current balance: " + balance;
          }
          case BALANCE_ENQUIRY -> {
            String balance = moneyFormat(bankAccount, null);
            return "Current Balance : " + balance;
          }
          case STATEMENT -> {
            List<Statement> statements = statementRepo.findByAccountNo(accountDto.getAccountNo());
            if (!statements.isEmpty()){
              List<StatementResponse> response = new ArrayList<>();
              statements.forEach(statement -> {
                StatementResponse statementResponse = new StatementResponse();
                statementResponse.setType(statement.getType());
                statementResponse.setMessage(statement.getMessage());
                statementResponse.setDateTime(statement.getDateTime());
                response.add(statementResponse);
              });
              return response;
            }else {
              return  "No statements found for this account.";
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
      throw new BankAccountNotFoundException("Invalid account number. Please check and try again.");
    }
  }

  public String moneyFormat(BankAccount bankAccount, BigDecimal money) {
    Locale indianLocale = new Locale("en", "IN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(indianLocale);
    if (Objects.nonNull(bankAccount)) {
      return currencyFormatter.format(bankAccount.getBankBalance());
    } else {
      return currencyFormatter.format(money);
    }
  }
}
