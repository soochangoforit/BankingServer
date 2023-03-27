package transfer.banking.server.domain.trasaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.common.BaseTimeEntity;

/**
 * 거래 내역 정보를 담는 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "TRANSACTION_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "TRANSACTION_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
@Table(name = "transactions")
public class Transaction extends BaseTimeEntity {

  /**
   * 거래 내역의 고유 ID. Sequence 전략을 사용한다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTION_SEQ_GENERATOR")
  private Long id;

  /**
   * 거래 송신자
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TRANSACTION_SENDER"))
  private Account sender;

  /**
   * 거래 수신자
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TRANSACTION_RECEIVER"))
  private Account receiver;

  /**
   * 거래 금액
   */
  @Column(nullable = false)
  private BigDecimal amount;

  /**
   * 거래 후 송신자 잔액
   */
  @Column(nullable = false)
  private BigDecimal senderLeftBalance;

  /**
   * 거래 후 수신자 잔액
   */
  @Column(nullable = false)
  private BigDecimal receiverLeftBalance;

  @Builder
  public Transaction(Long id, Account sender, Account receiver, BigDecimal amount, BigDecimal senderLeftBalance, BigDecimal receiverLeftBalance) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.senderLeftBalance = senderLeftBalance;
    this.receiverLeftBalance = receiverLeftBalance;
  }








}
