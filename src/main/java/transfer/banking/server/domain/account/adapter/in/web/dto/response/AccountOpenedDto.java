package transfer.banking.server.domain.account.adapter.in.web.dto.response;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class AccountOpenedDto {
  /**
   * 계좌를 개설한 사용자의 ID
   */
  private final Long memberId;

  /**
   * 계좌를 개설한 사용자의 이름
   */
  private final String memberName;

  /**
   * 계좌 번호
   */
  private final String accountNumber;

  /**
   * 계좌 이름
   */
  private final String accountName;

  /**
   * 은행
   */
  private final String bank;

  /**
   * 잔액
   */
  private final BigDecimal balance;

 

  public AccountOpenedDto(AccountOpenedDtoCommand accountOpenedDtoCmd) {
    this.memberId = accountOpenedDtoCmd.getMemberId();
    this.memberName = accountOpenedDtoCmd.getMemberName();
    this.accountNumber = accountOpenedDtoCmd.getAccountNumber();
    this.accountName = accountOpenedDtoCmd.getAccountName();
    this.bank = accountOpenedDtoCmd.getBank();
    this.balance = accountOpenedDtoCmd.getBalance();
  }
}
