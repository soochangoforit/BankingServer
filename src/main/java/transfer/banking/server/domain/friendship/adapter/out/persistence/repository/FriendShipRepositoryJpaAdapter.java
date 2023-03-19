package transfer.banking.server.domain.friendship.adapter.out.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.friendship.adapter.out.persistence.entity.FriendShip;
import transfer.banking.server.domain.friendship.application.mapper.FriendShipMapper;
import transfer.banking.server.domain.friendship.application.port.out.FriendShipRepositoryPort;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;


@Repository
@RequiredArgsConstructor
public class FriendShipRepositoryJpaAdapter implements FriendShipRepositoryPort {

  private final FriendShipRepository friendShipRepository;
  private final FriendShipMapper friendShipMapper;

  @Override
  @Transactional(readOnly = true)
  public boolean existsByMemberIdAndFriendIdAndFriendAccountNum(Long memberId, MemberAccountDomain friendAccountDomain) {
    Long friendId = friendAccountDomain.getMember().getId();
    String friendAccountNumber = friendAccountDomain.getAccount().getAccountNumber();
    return friendShipRepository.findByMemberIdAndFriendId(memberId, friendId, friendAccountNumber).isPresent();
  }

  @Override
  @Transactional
  public void save(Long memberId, MemberAccountDomain friendAccountDomain) {
    FriendShip friendShipEntity = friendShipMapper.toEntity(memberId, friendAccountDomain);
    friendShipRepository.save(friendShipEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> searchMyFriends(Long memberId) {
    return friendShipRepository.findByMemberId(memberId);
  }
}
