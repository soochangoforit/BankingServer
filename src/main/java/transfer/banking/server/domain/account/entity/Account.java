package transfer.banking.server.domain.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "accounts")
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
  private String accountNumber;

  /**
   * 계좌의 이름
   */
  private String accountName;


  /**
   * 계좌의 잔액
   */
  private BigDecimal balance;

  @Builder
  public Account(String accountNumber, String accountName, BigDecimal balance) {
    this.accountNumber = accountNumber;
    this.accountName = accountName;
    this.balance = balance;
  }


}
