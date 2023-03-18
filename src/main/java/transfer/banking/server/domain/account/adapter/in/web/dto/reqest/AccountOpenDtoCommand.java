package transfer.banking.server.domain.account.adapter.in.web.dto.reqest;

import java.math.BigDecimal;
import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;

@Getter
public class AccountOpenDtoCommand {

  /**
   * 계좌를 개설하고자 하는 사용자의 ID
   */
  private final Long memberId;

  /**
   * 은행
   */
  private final Bank bank;

  /**
   * 계좌 이름
   */
  private final String accountName;

  public AccountOpenDtoCommand(AccountOpenDto accountOpenDto) {
    this.memberId = accountOpenDto.getMemberId();
    this.bank = accountOpenDto.getBank();
    this.accountName = accountOpenDto.getAccountName();
  }

  public AccountDomain toAccountDomain(String accountNumber) {
    return AccountDomain.builder()
        .accountNumber(accountNumber)
        .accountName(accountName)
        .bank(bank)
        .balance(BigDecimal.ZERO)
        .build();
  }
}
