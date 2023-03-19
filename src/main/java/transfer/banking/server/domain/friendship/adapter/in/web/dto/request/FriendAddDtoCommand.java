package transfer.banking.server.domain.friendship.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

@Getter
public class FriendAddDtoCommand {

  private final Long memberId;
  private final Bank friendAccountBank;
  private final String friendAccountNumber;

  @Builder
  public FriendAddDtoCommand(Long memberId, Bank friendAccountBank, String friendAccountNumber) {
    this.memberId = memberId;
    this.friendAccountBank = friendAccountBank;
    this.friendAccountNumber = friendAccountNumber;
  }

}
