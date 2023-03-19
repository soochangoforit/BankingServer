package transfer.banking.server.domain.friendship.adapter.out.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.friendship.adapter.out.persistence.entity.FriendShip;
import transfer.banking.server.domain.friendship.application.mapper.FriendShipMapper;
import transfer.banking.server.domain.friendship.application.port.out.FriendShipRepositoryPort;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

/**
 * 친구 관계 Repository Jpa Adapter
 * 입력 값으로 도메인 객체 혹은 Primitive Type 을 받아서 처리한다.
 * 응답 값으로 도메인 객체 혹은 Primitive Type 을 반환한다.
 * 내부적으로 도메인 객체를 Entity 로 변환하고, Entity 를 도메인 객체로 변환한다.
 */
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
    FriendShip friendShipEntity = friendShipMapper.toJpaEntity(memberId, friendAccountDomain);
    friendShipRepository.save(friendShipEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> searchMyFriends(Long memberId) {
    return friendShipRepository.findByMemberId(memberId);
  }
}
