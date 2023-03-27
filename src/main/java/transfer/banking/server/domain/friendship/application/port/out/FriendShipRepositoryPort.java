package transfer.banking.server.domain.friendship.application.port.out;

import java.util.List;

/**
 * 친구 관계 Repository Port
 */
public interface FriendShipRepositoryPort {

  boolean existsByMemberIdAndFriendId(Long memberId, Long friendId);

  void save(Long memberId, Long friendId);

  List<Long> searchMyFriends(Long memberId);
}
