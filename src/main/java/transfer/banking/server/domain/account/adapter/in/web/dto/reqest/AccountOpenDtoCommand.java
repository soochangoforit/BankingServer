package transfer.banking.server.domain.account.adapter.in.web.dto.reqest;

import java.math.BigDecimal;
import lombok.Builder;
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

  @Builder
  public AccountOpenDtoCommand(Long memberId, Bank bank, String accountName) {
    this.memberId = memberId;
    this.bank = bank;
    this.accountName = accountName;
  }

  /**
   * 파라미터로 받은 계좌 번호를 이용해서 AccountDomain 객체를 생성한다.
   *
   * @param accountNumber 생성된 계좌 번호
   * @return 생성된 AccountDomain 객체
   */
  public AccountDomain toAccountDomain(String accountNumber) {
    return AccountDomain.builder()
        .accountNumber(accountNumber)
        .accountName(accountName)
        .bank(bank)
        .balance(BigDecimal.ZERO)
        .build();
  }
}
