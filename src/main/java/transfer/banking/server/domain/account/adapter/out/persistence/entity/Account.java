package transfer.banking.server.domain.account.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import transfer.banking.server.domain.common.BaseTimeEntity;

/**
 * 계좌 정보를 담는 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "ACCOUNT_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "ACCOUNT_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
@Table(
    name = "accounts",
    indexes = @Index(name = "idx_bank_account_number", columnList = "bank, account_number", unique = true)
)
public class Account extends BaseTimeEntity {

  /**
   * 계좌의 고유 ID. Sequence 전략을 사용한다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GENERATOR")
  private Long id;

  /**
   * 계좌의 고유 번호
   */
  @Column(nullable = false, unique = true, name = "account_number")
  private String accountNumber;

  /**
   * 계좌의 이름
   */
  @Column(nullable = false)
  private String accountName;

  /**
   * 은행 유형
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Bank bank;


  /**
   * 계좌의 잔액
   */
  @Column(nullable = false)
  private BigDecimal balance;

  @Builder
  public Account(Long id, String accountNumber, String accountName, Bank bank, BigDecimal balance) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.accountName = accountName;
    this.bank = bank;
    this.balance = balance;
  }


}
