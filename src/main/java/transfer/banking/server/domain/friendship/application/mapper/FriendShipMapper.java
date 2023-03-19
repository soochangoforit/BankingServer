package transfer.banking.server.domain.friendship.application.mapper;

import org.springframework.stereotype.Component;
import transfer.banking.server.domain.friendship.adapter.out.persistence.entity.FriendShip;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

/**
 * 친구 관계 Mapper
 * 도메인 객체를 Entity 로 변환하고, Entity 를 도메인 객체로 변환한다.
 */
@Component
public class FriendShipMapper {


  public FriendShip toJpaEntity(Long memberId, MemberAccountDomain friendAccountDomain) {
    return FriendShip.builder()
        .memberId(memberId)
        .friendId(friendAccountDomain.getMember().getId())
        .transactionCount(0L)
        .friendAccountNumber(friendAccountDomain.getAccount().getAccountNumber())
        .build();
  }
}
