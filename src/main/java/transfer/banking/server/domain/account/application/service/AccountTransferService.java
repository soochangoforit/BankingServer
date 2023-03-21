package transfer.banking.server.domain.account.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountTransferDtoCommand;
import transfer.banking.server.domain.account.application.port.in.AccountTransferUseCase;
import transfer.banking.server.domain.friendship.application.service.FriendShipService;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

/**
 * 계좌 이체 복합 서비스
 */
@Service
@RequiredArgsConstructor
public class AccountTransferService implements AccountTransferUseCase {

  private final AccountService accountService;
  private final MemberAccountService memberAccountService;
  private final FriendShipService friendShipService;
  private final TransferService transferService;

  /**
   * 계좌 이체
   * - 이체를 할 수 있는 계좌인지 확인 (친구 관계인지 확인)
   * - 계좌 이체할 만큼의 돈의 이상을 가지고 있는가?
   * - 계좌 이체
   */
  @Override
  public void transfer(AccountTransferDtoCommand command) {

    // 친구 계좌 도메인을 조회 (실제 존재하는 계좌인지 판단 가능) - 동시성 고려 X
    MemberAccountDomain friendAccountDomain = memberAccountService.findFriendAccountByNumAndBank(
        command.getToAccountNumber(), command.getFriendBank());

    // 계좌 이체를 할 수 있는 계좌인지 확인 (친구 관계인지 검증) - 동시성 고려 X
    friendShipService.canTransferOnlyWithFriend(command.getMemberId(), friendAccountDomain);

    // 실제 이체 트랜잭션
    transferService.transfer(command.getMyBank(), command.getFromAccountNumber(), friendAccountDomain, command.getTransferAmount());
  }
}
