package transfer.banking.server.domain.friendship.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.MemberIdCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsAccountCommand;
import transfer.banking.server.domain.friendship.application.port.in.FriendSearchUseCase;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

@Service
@RequiredArgsConstructor
public class FriendSearchService implements FriendSearchUseCase {

  private final FriendShipService friendShipService;
  private final MemberAccountService memberAccountService;

  @Override
  public List<MyFriendsAccountCommand> searchMyFriends(MemberIdCommand command) {
    Long memberId = command.getMemberId();
    List<String> myFriendsAccountNumbers = friendShipService.searchMyFriends(memberId);
    List<MemberAccountDomain> domains = memberAccountService.searchFriendsAccount(
        myFriendsAccountNumbers);
    return MyFriendsAccountCommand.toCommands(domains);
  }

}
