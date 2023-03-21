package transfer.banking.server.domain.account.adapter.in.web.dto.reqest;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 계좌 이체 요청 DTO
 */
@Getter
@NoArgsConstructor
public class AccountTransferDto {

  private Long memberId;
  private Bank myBank;
  private String fromAccountNumber;
  private Bank friendBank;
  private String toAccountNumber;
  private BigDecimal transferAmount;


  /**
   * AccountTransferDto 를 AccountTransferDtoCommand 로 변환한다.
   *
   * @return AccountTransferDtoCommand , 유스케이스 계층에서 사용한다.
   */
  public AccountTransferDtoCommand toCommand() {
    return AccountTransferDtoCommand.builder()
        .memberId(memberId)
        .myBank(myBank)
        .fromAccountNumber(fromAccountNumber)
        .friendBank(friendBank)
        .toAccountNumber(toAccountNumber)
        .transferAmount(transferAmount)
        .build();
  }
}
