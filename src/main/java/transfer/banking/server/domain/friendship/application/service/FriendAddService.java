package transfer.banking.server.domain.friendship.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDtoCommand;
import transfer.banking.server.domain.friendship.application.port.in.FriendAddUseCase;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendAddService implements FriendAddUseCase {

  private final FriendShipService friendShipService;
  private final MemberAccountService memberAccountService;

  @Override
  public void addFriend(FriendAddDtoCommand command) {
    // Account 를 먼저 얻어, id 값을 찾는다.
    MemberAccountDomain friendAccountDomain = memberAccountService.findFriendAccountByNumAndBank(
        command.getFriendAccountNumber(), command.getFriendAccountBank());

    // 친구 관계가 이미 존재하는지 확인한다.
    friendShipService.checkIfFriendShipExists(command.getMemberId(),
        friendAccountDomain);

    // 친구 관계가 아니라면, 친구 관계를 추가한다.
    friendShipService.saveFriendShip(command.getMemberId(),
        friendAccountDomain);
  }
}
