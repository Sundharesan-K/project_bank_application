package com.springboot_project.bank_application.service.impl;

import static com.springboot_project.bank_application.constant.Constant.COMPLETED;
import static com.springboot_project.bank_application.constant.Constant.INVALID_ACCOUNT;
import static com.springboot_project.bank_application.constant.Constant.MONEY_TRANSFER_SUCCESS;
import static com.springboot_project.bank_application.model.AccountStatusType.ACTIVE;
import static com.springboot_project.bank_application.model.BankOptions.TRANSFER;

import com.springboot_project.bank_application.dto.MoneyTransferDto;
import com.springboot_project.bank_application.exception.AccountNotFoundException;
import com.springboot_project.bank_application.model.Account;
import com.springboot_project.bank_application.model.Transaction;
import com.springboot_project.bank_application.repo.AccountRepo;
import com.springboot_project.bank_application.repo.TransactionRepo;
import com.springboot_project.bank_application.service.MoneyTransferService;
import com.springboot_project.bank_application.util.MoneyTransactionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

  private final AccountRepo accountRepo;
  private final TransactionRepo transactionRepo;

  @Override
  public String transferMoney(MoneyTransferDto moneyTransferDto) {
    Account fromAccount = accountRepo.findByAccountNo(moneyTransferDto.getFromAccountNo());
    Account toAccount = accountRepo.findByAccountNo(moneyTransferDto.getToAccountNo());

    if (Objects.isNull(fromAccount) && Objects.isNull(toAccount)) {
      throw new AccountNotFoundException(INVALID_ACCOUNT);
    }

    if (!fromAccount.getStatus().equals(ACTIVE.name()) || !toAccount.getStatus()
        .equals(ACTIVE.name())) {
      throw new IllegalArgumentException("One of the accounts is inactive.");
    }
    BigDecimal subtractAmount = MoneyTransactionService.withdrawOrTransferMoney(
        fromAccount.getBankBalance(),
        moneyTransferDto.getAmount(), TRANSFER.name());
    fromAccount.setBankBalance(subtractAmount);
    fromAccount.setUpdatedAt(LocalDateTime.now());
    BigDecimal addAmount = MoneyTransactionService.depositOrTransferMoney(
        toAccount.getBankBalance(),
        moneyTransferDto.getAmount(), TRANSFER.name());
    toAccount.setBankBalance(addAmount);
    toAccount.setUpdatedAt(LocalDateTime.now());
    setTransactionDetails(moneyTransferDto);
    accountRepo.save(fromAccount);
    accountRepo.save(toAccount);
    return MONEY_TRANSFER_SUCCESS;
  }

  private void setTransactionDetails(MoneyTransferDto moneyTransferDto) {
    Transaction transaction = new Transaction();
    transaction.setFromAccountNo(moneyTransferDto.getFromAccountNo());
    transaction.setToAccountNo(moneyTransferDto.getToAccountNo());
    transaction.setAmount(moneyTransferDto.getAmount().doubleValue());
    transaction.setTransactionType(TRANSFER.name());
    transaction.setStatus(COMPLETED);
    transaction.setCreatedAt(LocalDateTime.now());
    transactionRepo.save(transaction);
  }
}
