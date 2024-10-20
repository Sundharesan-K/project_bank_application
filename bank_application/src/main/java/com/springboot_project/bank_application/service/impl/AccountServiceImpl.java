package com.springboot_project.bank_application.service.impl;

import static com.springboot_project.bank_application.constant.Constant.ACCOUNT_CREATED_SUCCESS;
import static com.springboot_project.bank_application.constant.Constant.ALREADY_IN_PIN;
import static com.springboot_project.bank_application.constant.Constant.AUTHENTICATION_FAILED;
import static com.springboot_project.bank_application.constant.Constant.COMPLETED;
import static com.springboot_project.bank_application.constant.Constant.CURRENT_BALANCE;
import static com.springboot_project.bank_application.constant.Constant.DEPOSIT_MONEY;
import static com.springboot_project.bank_application.constant.Constant.ERROR_OCCURRED;
import static com.springboot_project.bank_application.constant.Constant.INCORRECT_PIN;
import static com.springboot_project.bank_application.constant.Constant.INVALID_ACCOUNT;
import static com.springboot_project.bank_application.constant.Constant.MONEY_DEPOSIT_SUCCESS;
import static com.springboot_project.bank_application.constant.Constant.MONEY_WITHDRAW_SUCCESS;
import static com.springboot_project.bank_application.constant.Constant.NO_STATEMENT_FOUND;
import static com.springboot_project.bank_application.constant.Constant.PIN_SET_SUCCESS;
import static com.springboot_project.bank_application.constant.Constant.USER_NOT_FOUND;
import static com.springboot_project.bank_application.constant.Constant.WITHDRAW_MONEY;
import static com.springboot_project.bank_application.constant.Constant.YOUR_ACCOUNT_ALREADY_ACTIVE;
import static com.springboot_project.bank_application.model.AccountStatusType.ACTIVE;
import static com.springboot_project.bank_application.model.AccountType.SAVINGS;
import static com.springboot_project.bank_application.model.BankOptions.DEPOSIT;
import static com.springboot_project.bank_application.model.BankOptions.WITHDRAW;
import static com.springboot_project.bank_application.util.GenerateRandomNumber.generateRandom12DigitNumber;
import static com.springboot_project.bank_application.util.GenerateRandomNumber.generateTransactionId;

import com.springboot_project.bank_application.dao.AccountDao;
import com.springboot_project.bank_application.dao.BranchDao;
import com.springboot_project.bank_application.dto.AccountDto;
import com.springboot_project.bank_application.dto.StatementResponse;
import com.springboot_project.bank_application.exception.AccountNotFoundException;
import com.springboot_project.bank_application.exception.ActiveException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

  private final JWTService jwtService;
  private final UserRepo userRepo;
  private final AccountRepo accountRepo;
  private final PasswordEncoder encoder;
  private final StatementRepo statementRepo;
  private final BranchDao branchDao;
  private final AccountDao accountDao;

  @Override
  public String accountCreate(String auth) {
    auth = auth.substring(7);
    String emailId = jwtService.extractUserName(auth);
    if (ObjectUtils.isEmpty(emailId)) {
      log.error("Authentication failed, token is invalid.");
      throw new UsernameNotFoundException(AUTHENTICATION_FAILED);
    }
    Users user = userRepo.findByEmailId(emailId);
    if (ObjectUtils.isEmpty(user)) {
      log.error("User with email {} not found", emailId);
      throw new UsernameNotFoundException(USER_NOT_FOUND);
    }
    Account account = accountRepo.findByAccountHolderName(
        user.getUsername() + " " + user.getLastname());
    if (!ObjectUtils.isEmpty(account) && account.getStatus().equals(ACTIVE.name())) {
      log.warn("Account already active for user: {}", user.getEmailId());
      throw new ActiveException(YOUR_ACCOUNT_ALREADY_ACTIVE);
    }
    return createAccountForUser(user);
  }

  private String createAccountForUser(Users user) {
    Account account = new Account();
    account.setAccountHolderName(user.getUsername() + " " + user.getLastname());
    account.setAccountNo(generateRandom12DigitNumber());
    setBranchDetails(user, account);
    account.setAccountType(SAVINGS.name());
    account.setBankBalance(BigDecimal.ZERO);
    account.setStatus(ACTIVE.name());
    account.setCreatedAt(LocalDateTime.now());
    account.setUpdatedAt(LocalDateTime.now());
    accountRepo.save(account);
    log.info("Account created successfully for user: {}", user.getEmailId());
    return ACCOUNT_CREATED_SUCCESS;
  }

  private void setBranchDetails(Users user, Account account) {
    Branches branches = branchDao.findBranch(user.getLocation().getAddress());
    if (Objects.nonNull(branches)) {
      account.setBankName(branches.getBankName());
      account.setLocation(branches.getLocation());
      account.setBranchId(branches.getBranchId());
      account.setIfscCode(branches.getIfscCode());
    }
  }

  @Override
  public String setPinForAccount(AccountDto accountDto) {
    Account account = getActiveAccountByNumber(accountDto.getAccountNo());
    if (Objects.isNull(account.getSecretPinNo())) {
      account.setSecretPinNo(encoder.encode(accountDto.getPin()));
      accountRepo.save(account);
      return PIN_SET_SUCCESS;
    } else {
      throw new AccountNotFoundException(ALREADY_IN_PIN);
    }
  }

  @Override
  public Object clickOptions(AccountDto accountDto) {
    Account account = getActiveAccountByNumber(accountDto.getAccountNo());
    if (encoder.matches(accountDto.getPin(), account.getSecretPinNo())) {
      switch (BankOptions.valueOf(accountDto.getOption())) {
        case WITHDRAW -> {
          BigDecimal amount = MoneyTransactionService.withdrawOrTransferMoney(
              account.getBankBalance(), accountDto.getAmount(), accountDto.getOption());
          account.setBankBalance(amount);
          account.setUpdatedAt(LocalDateTime.now());
          accountRepo.save(account);
          String balance = moneyFormat(account, null);
          buildStatement(accountDto, account, WITHDRAW);
          return MONEY_WITHDRAW_SUCCESS + balance;
        }
        case DEPOSIT -> {
          BigDecimal amount = MoneyTransactionService.depositOrTransferMoney(
              account.getBankBalance(), accountDto.getAmount(), accountDto.getOption());
          account.setBankBalance(amount);
          account.setUpdatedAt(LocalDateTime.now());
          accountRepo.save(account);
          String balance = moneyFormat(account, null);
          buildStatement(accountDto, account, DEPOSIT);
          return MONEY_DEPOSIT_SUCCESS + balance;
        }
        case BALANCE_ENQUIRY -> {
          String balance = moneyFormat(account, null);
          return CURRENT_BALANCE + balance;
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
            return NO_STATEMENT_FOUND;
          }
        }
        default -> {
          return ERROR_OCCURRED;
        }
      }
    } else {
      log.error("Incorrect PIN provided.");
      throw new IncorrectPinException(INCORRECT_PIN);
    }
  }

  private void buildStatement(AccountDto accountDto, Account account, BankOptions option) {
    Statement statement = new Statement();
    statement.setTransactionId(generateTransactionId());
    statement.setAccountId(account.getId());
    statement.setAccountNo(account.getAccountNo());
    statement.setType(option.name());
    statement.setMessage((option == WITHDRAW ? WITHDRAW_MONEY : DEPOSIT_MONEY)
        + moneyFormat(null, accountDto.getAmount()));
    statement.setAmount(accountDto.getAmount().doubleValue());
    statement.setStatus(COMPLETED);
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

  @Override
  public String updatePin(AccountDto accountDto) throws Exception {
    Account account = accountRepo.findByAccountNo(accountDto.getAccountNo());
    if (Objects.nonNull(account) && account.getStatus().equals(ACTIVE.name())) {
      String newEncoderPin = encoder.encode(accountDto.getPin());
      return accountDao.updatePin(account, newEncoderPin);
    } else {
      throw new AccountNotFoundException(INVALID_ACCOUNT);
    }
  }

  private Account getActiveAccountByNumber(String accountNo) {
    Account account = accountRepo.findByAccountNo(accountNo);
    if (Objects.isNull(account) || !ACTIVE.name().equals(account.getStatus())) {
      log.error("Account not found or inactive for accountNo: {}", accountNo);
      throw new AccountNotFoundException(INVALID_ACCOUNT);
    }
    return account;
  }
}
