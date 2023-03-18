package transfer.banking.server.domain.friendship.application.mapper;

import org.springframework.stereotype.Component;
import transfer.banking.server.domain.friendship.adapter.out.persistence.entity.FriendShip;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

@Component
public class FriendShipMapper {


  public FriendShip toEntity(Long memberId, MemberAccountDomain friendAccountDomain) {
    return FriendShip.builder()
        .memberId(memberId)
        .friendId(friendAccountDomain.getMember().getId())
        .build();
  }
}
