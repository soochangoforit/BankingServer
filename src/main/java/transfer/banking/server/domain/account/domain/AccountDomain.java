package transfer.banking.server.domain.account.domain;


import static transfer.banking.server.global.exception.ErrorCode.NOT_ENOUGH_BALANCE;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.exception.NotEnoughBalanceException;

@Getter
public class AccountDomain {

  private final Long id;
  private final String accountNumber;
  private final String accountName;
  private final Bank bank;
  private BigDecimal balance;

  @Builder
  public AccountDomain(Long id, String accountNumber, String accountName, Bank bank,
      BigDecimal balance) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.accountName = accountName;
    this.bank = bank;
    this.balance = balance;
  }

  /**
   * 계좌 이체 가능한 금액인지 확인한다.
   *
   * @param transferAmount 이체하고자 하는 금액
   */
  public void checkIfEnoughBalance(BigDecimal transferAmount) {
    if (balance.compareTo(transferAmount) < 0) {
      throw new NotEnoughBalanceException(NOT_ENOUGH_BALANCE);
    }
  }

  /**
   * 계좌에서 금액을 출금한다.
   *
   * @param transferAmount 출금하고자 하는 금액
   */
  public void withdraw(BigDecimal transferAmount) {
    this.checkIfEnoughBalance(transferAmount);
    this.balance = this.balance.subtract(transferAmount);
  }

  /**
   * 계좌에 금액을 입금한다.
   *
   * @param transferAmount 입금하고자 하는 금액
   */
  public void deposit(BigDecimal transferAmount) {
    this.balance = this.balance.add(transferAmount);
  }
}
