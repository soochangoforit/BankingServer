package transfer.banking.server.domain.friendship.application.port.out;

import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

public interface FriendShipRepositoryPort {

  boolean existsByMemberIdAndFriendId(Long memberId, Long friendId);

  void save(Long memberId, MemberAccountDomain friendAccountDomain);
}
