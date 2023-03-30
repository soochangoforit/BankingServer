package transfer.banking.server.domain.friendship.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import transfer.banking.server.domain.common.BaseTimeEntity;

/**
 * 친구 관계를 담는 Entity
 */

@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "FRIENDSHIP_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "FRIENDSHIP_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
@Table(
    name = "friendships",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "friend_id"})
    }
)
public class FriendShip extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FRIENDSHIP_SEQ_GENERATOR")
  private Long id;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Column(name = "friend_id", nullable = false)
  private Long friendId;


  @Builder
  public FriendShip(Long id, Long memberId, Long friendId) {
    this.id = id;
    this.memberId = memberId;
    this.friendId = friendId;
  }

}
