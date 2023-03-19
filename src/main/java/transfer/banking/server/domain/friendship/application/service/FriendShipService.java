package transfer.banking.server.domain.friendship.application.service;

import static transfer.banking.server.global.exception.ErrorCode.ALREADY_FRIEND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
   * @param friendAccountDomain 추가하고자 하는 친구 계좌 도메인 객체
   */
  public void checkIfFriendShipExists(Long memberId, MemberAccountDomain friendAccountDomain) {
    if (friendShipRepository.existsByMemberIdAndFriendIdAndFriendAccountNum(memberId, friendAccountDomain)) {
      throw new AlreadyFriendException(ALREADY_FRIEND);
    }
  }

  /**
   * 친구 관계를 저장
   *
   * @param memberId 친구 관계를 추가하고자 하는 멤버 id
   * @param friendAccountDomain 추가하고자 하는 친구 계좌 도메인 객체
   */
  public void saveFriendShip(Long memberId, MemberAccountDomain friendAccountDomain) {
    log.info("친구 관계를 저장합니다. memberId: {}, friendId: {}", memberId, friendAccountDomain.getMember().getId());
    friendShipRepository.save(memberId, friendAccountDomain);
  }

  public List<String> searchMyFriends(Long memberId) {
    log.info("친구 id 목록을 조회합니다. memberId: {}", memberId);
    return friendShipRepository.searchMyFriends(memberId);
  }
}
