package transfer.banking.server.domain.trasaction.adapter.in.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.trasaction.domain.TransactionDomain;

@Getter
public class TransactionHistoryResponseDtoCommand {

  // 받는 사람, 혹은 보내는 사람 될 수 있다.
  private final String otherAccountName;

  // 남은 잔액
  private final BigDecimal leftBalance;

  // 입금, 출금 금액
  private final BigDecimal amount;

  // 이체 날짜
  private final LocalDateTime transactionDate;

  public TransactionHistoryResponseDtoCommand(TransactionDomain transactionDomain, AccountDomain myAccountDomain) {

    // 내가 보낸 경우
    if (transactionDomain.getSender().getAccountName().equals(myAccountDomain.getAccountName())) {
      this.otherAccountName = transactionDomain.getReceiver().getAccountName();
      this.amount = transactionDomain.getAmount().negate();
      this.leftBalance = transactionDomain.getSenderLeftBalance();
    } else {
      this.otherAccountName = transactionDomain.getSender().getAccountName();
      this.amount = transactionDomain.getAmount();
      this.leftBalance = transactionDomain.getReceiverLeftBalance();
    }
    this.transactionDate = transactionDomain.getTransactionDate();
  }


}
