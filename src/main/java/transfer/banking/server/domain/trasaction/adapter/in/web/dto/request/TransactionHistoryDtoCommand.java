package transfer.banking.server.domain.trasaction.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 계좌 이체 내역 조회 요청 DTO Command
 */
@Getter
public class TransactionHistoryDtoCommand {
  private final Long memberId;
  private final Bank bank;
  private final String accountNumber;

  @Builder
  public TransactionHistoryDtoCommand(Long memberId, Bank bank, String accountNumber) {
    this.memberId = memberId;
    this.bank = bank;
    this.accountNumber = accountNumber;
  }
}
