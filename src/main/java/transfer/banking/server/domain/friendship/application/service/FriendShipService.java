package transfer.banking.server.domain.friendship.application.service;

import static transfer.banking.server.global.exception.ErrorCode.ALREADY_FRIEND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.friendship.application.exception.AlreadyFriendException;
import transfer.banking.server.domain.friendship.application.port.out.FriendShipRepositoryPort;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendShipService {

  private final FriendShipRepositoryPort friendShipRepository;

  public void checkIfFriendShipExists(Long memberId, Long friendId) {
    if (friendShipRepository.existsByMemberIdAndFriendId(memberId, friendId)) {
      throw new AlreadyFriendException(ALREADY_FRIEND);
    }
  }

  public void saveFriendShip(Long memberId, MemberAccountDomain friendAccountDomain) {
    log.info("친구 관계를 저장합니다. memberId: {}, friendId: {}", memberId, friendAccountDomain.getMember().getId());
    friendShipRepository.save(memberId, friendAccountDomain);
  }
}
