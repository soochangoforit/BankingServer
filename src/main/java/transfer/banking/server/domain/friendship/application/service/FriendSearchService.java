package transfer.banking.server.domain.friendship.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.MemberIdCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsAccountCommand;
import transfer.banking.server.domain.friendship.application.port.in.FriendSearchUseCase;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

/**
 * 친구 계좌 조회 복합 서비스
 */
@Service
@RequiredArgsConstructor
public class FriendSearchService implements FriendSearchUseCase {

  private final FriendShipService friendShipService;
  private final MemberAccountService memberAccountService;

  /**
   * 내가 등록한 친구 계좌 조회
   */
  @Override
  public List<MyFriendsAccountCommand> searchMyFriends(MemberIdCommand command) {
    Long memberId = command.getMemberId();
    List<String> myFriendsAccountNumbers = friendShipService.searchMyFriendsAccountNum(memberId);
    List<MemberAccountDomain> domains = memberAccountService.searchFriendsAccount(
        myFriendsAccountNumbers);
    return MyFriendsAccountCommand.toCommands(domains);
  }

}
