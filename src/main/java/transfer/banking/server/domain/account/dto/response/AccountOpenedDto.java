package transfer.banking.server.domain.account.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.member.entity.Member;

/**
 * 계좌 개설 응답 DTO
 */
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

  public AccountOpenedDto(Account savedAccount, Member member) {
    this.memberId = member.getId();
    this.memberName = member.getName();
    this.accountNumber = savedAccount.getAccountNumber();
    this.accountName = savedAccount.getAccountName();
    this.bank = savedAccount.getBank().name();
    this.balance = savedAccount.getBalance();
  }
}
