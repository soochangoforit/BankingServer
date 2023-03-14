package transfer.banking.server.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import transfer.banking.server.domain.common.BaseTimeEntity;

/**
 * 사용자 정보를 담는 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "MEMBER_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
@Table(name = "members")
public class Member extends BaseTimeEntity {

  /**
   * 사용자의 고유 ID. Sequence 전략을 사용한다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
  private Long id;

  /**
   * 사용자의 고유 아이디
   */
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * 사용자의 비밀번호
   */
  @Column(nullable = false)
  private String password;

  /**
   * 사용자의 이메일
   */
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * 사용자의 이름.
   */
  @Column(nullable = false)
  private String name;

  /**
   * 사용자의 전화번호
   */
  @Column(nullable = false, unique = true)
  private String phoneNumber;


  @Builder
  public Member(String username, String password, String email, String name, String phoneNumber) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }


}
