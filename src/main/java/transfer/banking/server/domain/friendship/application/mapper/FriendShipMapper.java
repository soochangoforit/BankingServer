package transfer.banking.server.domain.friendship.application.mapper;

import org.springframework.stereotype.Component;
import transfer.banking.server.domain.friendship.adapter.out.persistence.entity.FriendShip;

/**
 * 친구 관계 Mapper
 * 도메인 객체를 Entity 로 변환하고, Entity 를 도메인 객체로 변환한다.
 */
@Component
public class FriendShipMapper {


  public FriendShip toJpaEntity(Long memberId, Long friendId) {
    return FriendShip.builder()
        .memberId(memberId)
        .friendId(friendId)
        .build();
  }
}
