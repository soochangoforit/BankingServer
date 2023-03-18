package transfer.banking.server.domain.account.adapter.in.web.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;

@Getter
public class AccountOpenedDtoCommand {

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

  public AccountOpenedDtoCommand(AccountDomain accountDomain, MemberDomain memberDomain) {
    this.memberId = memberDomain.getId();
    this.memberName = memberDomain.getName();
    this.accountNumber = accountDomain.getAccountNumber();
    this.accountName = accountDomain.getAccountName();
    this.bank = accountDomain.getBank().toString();
    this.balance = accountDomain.getBalance();

  }

}
