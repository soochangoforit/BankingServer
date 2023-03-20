package transfer.banking.server.domain.account.adapter.in.web.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import transfer.banking.server.domain.account.domain.AccountDomain;

@Getter
public class AccountSearchDtoResCommand {

  private final Long accountId;
  private final String accountBank;
  private final String accountNumber;
  private final BigDecimal balance;

  public AccountSearchDtoResCommand(AccountDomain accountDomain, BigDecimal balance) {
    this.accountId = accountDomain.getId();
    this.accountBank = accountDomain.getBank().name();
    this.accountNumber = accountDomain.getAccountNumber();
    this.balance = balance;
  }

}
