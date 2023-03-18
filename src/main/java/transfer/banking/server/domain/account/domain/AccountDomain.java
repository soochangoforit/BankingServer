package transfer.banking.server.domain.account.domain;


import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

@Getter
public class AccountDomain {

  private final Long id;
  private final String accountNumber;
  private final String accountName;
  private final Bank bank;
  private final BigDecimal balance;

  @Builder
  public AccountDomain(Long id, String accountNumber, String accountName, Bank bank, BigDecimal balance) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.accountName = accountName;
    this.bank = bank;
    this.balance = balance;
  }

}
