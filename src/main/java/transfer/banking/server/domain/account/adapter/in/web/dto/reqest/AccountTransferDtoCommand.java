package transfer.banking.server.domain.account.adapter.in.web.dto.reqest;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 계좌 이체 요청 DTO 를 유스케이스에서 사용할 Command 로 변환한다.
 */
@Getter
public class AccountTransferDtoCommand {

  private final Long memberId;

  private final Bank myBank;
  private final String fromAccountNumber;
  private final Bank friendBank;
  private final String toAccountNumber;
  private final BigDecimal transferAmount;

  @Builder
  public AccountTransferDtoCommand(Long memberId, Bank myBank, String fromAccountNumber, Bank friendBank, String toAccountNumber, BigDecimal transferAmount) {
    this.memberId = memberId;
    this.myBank = myBank;
    this.fromAccountNumber = fromAccountNumber;
    this.friendBank = friendBank;
    this.toAccountNumber = toAccountNumber;
    this.transferAmount = transferAmount;
  }

}
