package transfer.banking.server.domain.trasaction.domain;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.domain.AccountDomain;

@Getter
public class TransactionDomain {

  private final Long id;

  /**
   * 거래 송신자
   */
  private final AccountDomain sender;

  /**
   * 거래 수신자
   */
  private final AccountDomain receiver;

  /**
   * 거래 금액
   */
  private final BigDecimal amount;

  /**
   * 거래 후 송신자 잔액
   */
  private final BigDecimal senderLeftBalance;

  /**
   * 거래 후 수신자 잔액
   */
  private final BigDecimal receiverLeftBalance;

  /**
   * 거래 일자
   */
  private final LocalDateTime transactionDate;

  @Builder
  public TransactionDomain(Long id, AccountDomain sender, AccountDomain receiver, BigDecimal amount,
      BigDecimal senderLeftBalance, BigDecimal receiverLeftBalance, LocalDateTime transactionDate) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.senderLeftBalance = senderLeftBalance;
    this.receiverLeftBalance = receiverLeftBalance;
    this.transactionDate = transactionDate;
  }

}
