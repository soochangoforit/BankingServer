package transfer.banking.server.domain.trasaction.adapter.in.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 계좌 이체 내역 조회 요청 DTO
 */
@Getter
@NoArgsConstructor
public class TransactionHistoryDto {

  private Long memberId;
  private Bank bank;
  private String accountNumber;


  /**
   * DTO -> Command
   *
   * @return TransactionHistoryDtoCommand
   */
  public TransactionHistoryDtoCommand toCommand() {
    return TransactionHistoryDtoCommand.builder()
        .memberId(memberId)
        .bank(bank)
        .accountNumber(accountNumber)
        .build();
  }
}
