package transfer.banking.server.domain.memberaccount.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.common.BaseTimeEntity;
import transfer.banking.server.domain.member.entity.Member;

/**
 * 사용자의 계좌 정보를 담는 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "MEMBER_ACCOUNT_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "MEMBER_ACCOUNT_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class MemberAccount extends BaseTimeEntity {

  /**
   * 사용자의 계좌 고유 ID. Sequence 전략을 사용한다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_ACCOUNT_SEQ_GENERATOR")
  private Long id;

  /**
   * 사용자의 고유 ID
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  /**
   * 사용자가 소유하고 있는 계좌의 고유 ID
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @Builder
  public MemberAccount(Member member, Account account) {
    this.member = member;
    this.account = account;
  }

}
