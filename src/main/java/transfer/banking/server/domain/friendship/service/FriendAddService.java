package transfer.banking.server.domain.friendship.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.friendship.dto.request.FriendAddDto;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

/**
 * 친구 추가 복합 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FriendAddService {

  private final FriendShipService friendShipService;
  private final MemberService memberService;
  private final MemberAccountService memberAccountService;

  /**
   * 친구 추가
   *
   * @param friendAddDto 친구 추가 요청 DTO
   */
  public void addFriend(FriendAddDto friendAddDto) {
    Member member = memberService.checkIfMemberExists(friendAddDto.getMemberId());
    Member friend = memberAccountService.findFriendByBankAndAccountNumber(friendAddDto.getFriendAccountBank(), friendAddDto.getFriendAccountNumber());
    friendShipService.checkAlreadyFriend(member, friend);
    friendShipService.addFriend(member, friend);
  }
}
