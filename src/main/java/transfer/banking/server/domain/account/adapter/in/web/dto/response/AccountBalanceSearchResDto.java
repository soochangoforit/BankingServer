package transfer.banking.server.domain.account.adapter.in.web.dto.response;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * 계좌 잔액 조회 응답 DTO
 */
@Getter
public class AccountBalanceSearchResDto {

  private final Long accountId;
  private final String accountBank;
  private final String accountNumber;
  private final BigDecimal balance;

  public AccountBalanceSearchResDto(AccountSearchDtoResCommand command) {
    this.accountId = command.getAccountId();
    this.accountBank = command.getAccountBank();
    this.accountNumber = command.getAccountNumber();
    this.balance = command.getBalance();
  }

}
