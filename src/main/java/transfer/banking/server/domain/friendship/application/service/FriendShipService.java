package transfer.banking.server.domain.friendship.application.service;

import static transfer.banking.server.global.exception.ErrorCode.ALREADY_FRIEND;
import static transfer.banking.server.global.exception.ErrorCode.CAN_TRANSFER_WITH_ONLY_FRIEND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.application.exception.CanTransferWithOnlyFriend;
import transfer.banking.server.domain.friendship.application.exception.AlreadyFriendException;
import transfer.banking.server.domain.friendship.application.port.out.FriendShipRepositoryPort;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

/**
 * 친구 관계 순수 서비스
 * 순수 서비스에서 예외 처리를 한다.
 * 파라미터로 도메인 객체 혹은 Primitive Type 을 받아서 처리한다.
 * 응답 값으로 도메인 객체 혹은 Primitive Type 을 반환한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FriendShipService {

  private final FriendShipRepositoryPort friendShipRepository;

  /**
   * 친구 관계가 존재하는지 확인
   *
   * @param memberId 친구 관계를 추가하고자 하는 멤버 id
   * @param friendId 추가하고자 하는 친구 id
   */
  public void checkIfFriendShipExists(Long memberId, Long friendId) {
    if (friendShipRepository.existsByMemberIdAndFriendId(memberId, friendId)) {
      throw new AlreadyFriendException(ALREADY_FRIEND);
    }
  }

  /**
   * 친구 관계를 저장
   *
   * @param memberId 친구 관계를 추가하고자 하는 멤버 id
   * @param friendId 추가하고자 하는 친구 id
   */
  public void saveFriendShip(Long memberId, Long friendId) {
    friendShipRepository.save(memberId, friendId);
  }

  /**
   * 내 친구 계좌번호 목록을 조회
   *
   * @param memberId 내 멤버 id
   * @return 내 친구 계좌번호 목록
   */
  public List<String> searchMyFriendsAccountNum(Long memberId) {
    return friendShipRepository.searchMyFriends(memberId);
  }

  public void canTransferOnlyWithFriend(Long memberId, MemberAccountDomain friendAccountDomain) {
    if (!friendShipRepository.existsByMemberIdAndFriendIdAndFriendAccountNum(memberId, friendAccountDomain)) {
      throw new CanTransferWithOnlyFriend(CAN_TRANSFER_WITH_ONLY_FRIEND);
    }
  }
}
