package transfer.banking.server.domain.friendship.service;

import static transfer.banking.server.global.exception.ErrorCode.ALREADY_FRIEND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.friendship.entity.FriendShip;
import transfer.banking.server.domain.friendship.exception.AlreadyFriendException;
import transfer.banking.server.domain.friendship.repository.FriendShipRepository;
import transfer.banking.server.domain.member.entity.Member;

/**
 * 친구 관계 순수 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FriendShipService {

  private final FriendShipRepository friendShipRepository;

  /**
   * 친구 추가
   *
   * @param member 친구 추가 API 를 요청한 회원
   * @param friend 추가하고자 하는 친구
   */
  @Transactional
  public void addFriend(Member member, Member friend) {
    log.info("친구를 추가합니다. member.name: {}, friend.name: {}", member.getName(), friend.getName());
    FriendShip friendShip = new FriendShip(member.getId(), friend.getId());
    friendShipRepository.save(friendShip);
  }

  /**
   * 이미 친구인지 확인
   *
   * @param member 친구 추가 API 를 요청한 회원
   * @param friend 추가하고자 하는 친구
   */
  @Transactional(readOnly = true)
  public void checkAlreadyFriend(Member member, Member friend) {
    log.info("이미 친구인지 확인합니다. member.name: {}, friend.name: {}", member.getName(), friend.getName());
    friendShipRepository.findByMemberIdAndFriendId(member.getId(), friend.getId())
        .ifPresent(friendShip -> {
          throw new AlreadyFriendException(ALREADY_FRIEND);
        });
  }
}
