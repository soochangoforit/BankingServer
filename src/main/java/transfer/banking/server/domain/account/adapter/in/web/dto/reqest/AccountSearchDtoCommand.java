package transfer.banking.server.domain.account.adapter.in.web.dto.reqest;

import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 계좌 잔액 조회 요청 DTO 를 Command 로 변환한다.
 */
@Getter
public class AccountSearchDtoCommand {

  private final Long memberId;
  private final Bank accountBank;
  private final String accountNumber;

  @Builder
  public AccountSearchDtoCommand(Long memberId, Bank accountBank, String accountNumber) {
    this.memberId = memberId;
    this.accountBank = accountBank;
    this.accountNumber = accountNumber;
  }

}
