package transfer.banking.server.domain.trasaction.adapter.in.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TransactionHistoryResponseDto {

  private final String otherAccountName;

  // 남은 잔액
  private final BigDecimal leftBalance;

  // 입금, 출금 금액
  private final BigDecimal amount;

  // 이체 날짜
  private final LocalDateTime transactionDate;

  public TransactionHistoryResponseDto(TransactionHistoryResponseDtoCommand command) {
    this.otherAccountName = command.getOtherAccountName();
    this.leftBalance = command.getLeftBalance();
    this.amount = command.getAmount();
    this.transactionDate = command.getTransactionDate();
  }

  public static List<TransactionHistoryResponseDto> of(List<TransactionHistoryResponseDtoCommand> commands) {
    return commands.stream()
        .map(TransactionHistoryResponseDto::new)
        .collect(Collectors.toList());
  }

}
