package transfer.banking.server.domain.trasaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import transfer.banking.server.domain.account.entity.Account;
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
  @JoinColumn(name = "sender_id", nullable = false)
  private Account sender;

  /**
   * 거래 수신자
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id", nullable = false)
  private Account receiver;

  /**
   * 거래 금액
   */
  @Column(nullable = false)
  private BigDecimal amount;

  @Builder
  public Transaction(Account sender, Account receiver, BigDecimal amount) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
  }








}
