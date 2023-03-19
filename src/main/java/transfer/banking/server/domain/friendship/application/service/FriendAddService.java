package transfer.banking.server.domain.friendship.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDtoCommand;
import transfer.banking.server.domain.friendship.application.port.in.FriendAddUseCase;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

/**
 * 친구 추가 복합 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FriendAddService implements FriendAddUseCase {

  private final FriendShipService friendShipService;
  private final MemberAccountService memberAccountService;

  /**
   * 친구 추가
   * - 친구 계좌 번호와 은행을 통해서 MemberAccountDomain 객체를 조회한다.
   * - 이미 친구 계좌를 추가한 이력이 있는지 확인한다.
   * - 친구 계좌를 추가한다.
   * @param command 친구 추가 정보를 담은 Command 객체
   */
  @Override
  public void addFriend(FriendAddDtoCommand command) {

    MemberAccountDomain friendAccountDomain = memberAccountService.findFriendAccountByNumAndBank(
        command.getFriendAccountNumber(), command.getFriendAccountBank());

    friendShipService.checkIfFriendShipExists(command.getMemberId(),
        friendAccountDomain);

    friendShipService.saveFriendShip(command.getMemberId(),
        friendAccountDomain);
  }
}
