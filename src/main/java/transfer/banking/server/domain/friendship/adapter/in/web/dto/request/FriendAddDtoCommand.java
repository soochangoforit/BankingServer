package transfer.banking.server.domain.friendship.adapter.in.web.dto.request;

import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

@Getter
public class FriendAddDtoCommand {

  private final Long memberId;
  private final Bank friendAccountBank;
  private final String friendAccountNumber;

  public FriendAddDtoCommand(FriendAddDto friendAddDto) {
    this.memberId = friendAddDto.getMemberId();
    this.friendAccountBank = friendAddDto.getFriendAccountBank();
    this.friendAccountNumber = friendAddDto.getFriendAccountNumber();
  }

}
