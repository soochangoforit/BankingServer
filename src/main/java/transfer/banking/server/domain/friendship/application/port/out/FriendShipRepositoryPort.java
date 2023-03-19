package transfer.banking.server.domain.friendship.application.port.out;

import java.util.List;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

/**
 * 친구 관계 Repository Port
 */
public interface FriendShipRepositoryPort {

  boolean existsByMemberIdAndFriendIdAndFriendAccountNum(Long memberId, MemberAccountDomain friendAccountDomain);

  void save(Long memberId, MemberAccountDomain friendAccountDomain);

  List<String> searchMyFriends(Long memberId);
}
