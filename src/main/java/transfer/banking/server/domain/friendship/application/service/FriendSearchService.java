package transfer.banking.server.domain.friendship.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.MemberIdCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsInfoCommand;
import transfer.banking.server.domain.friendship.application.port.in.FriendSearchUseCase;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 친구 조회 복합 서비스
 */
@Service
@RequiredArgsConstructor
public class FriendSearchService implements FriendSearchUseCase {

  private final FriendShipService friendShipService;
  private final MemberService memberService;

  /**
   * 내가 등록한 친구 목록 조회
   */
  @Override
  public List<MyFriendsInfoCommand> searchMyFriends(MemberIdCommand command) {
    Long memberId = command.getMemberId();
    List<Long> myFriendIds = friendShipService.searchMyFriendsId(memberId);
    List<MemberDomain> friendDomains = memberService.findMemberByIds(myFriendIds);
    return MyFriendsInfoCommand.toCommands(friendDomains);
  }

}
