package transfer.banking.server.domain.account.adapter.in.web.dto.reqest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 계좌 개설 요청 DTO
 */
@Getter
@NoArgsConstructor
public class AccountOpenDto {

  /**
   * 계좌를 개설하고자 하는 사용자의 ID
   */
  private Long memberId;

  /**
   * 은행
   */
  private Bank bank;

  /**
   * 계좌 이름
   */
  private String accountName;

  /**
   * AccountOpenDto 를 AccountOpenDtoCommand 로 변환한다.
   *
   * @return AccountOpenDtoCommand , 유스케이스 계층에서 사용한다.
   */
  public AccountOpenDtoCommand toCommand() {
    return AccountOpenDtoCommand.builder()
        .memberId(this.memberId)
        .bank(this.bank)
        .accountName(this.accountName)
        .build();
  }
}
