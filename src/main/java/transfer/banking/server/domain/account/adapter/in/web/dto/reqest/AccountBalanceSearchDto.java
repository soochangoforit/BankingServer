package transfer.banking.server.domain.account.adapter.in.web.dto.reqest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 계좌 잔액 조회 요청 DTO
 */
@Getter
@NoArgsConstructor
public class AccountBalanceSearchDto {

  /**
   * 계좌를 조회하고자 하는 사용자의 ID
   */
  private Long memberId;

  /**
   * 은행
   */
  private Bank accountBank;

  /**
   * 잔액을 확인하고자 하는 계좌 번호
   */
  private String accountNumber;

  /**
   * AccountSearchDto 를 AccountSearchDtoCommand 로 변환한다.
   * @return AccountSearchDtoCommand , 유스케이스 계층에서 사용한다.
   */
  public AccountSearchDtoCommand toCommand() {
    return AccountSearchDtoCommand.builder()
        .memberId(this.memberId)
        .accountBank(this.accountBank)
        .accountNumber(this.accountNumber)
        .build();
  }
}
