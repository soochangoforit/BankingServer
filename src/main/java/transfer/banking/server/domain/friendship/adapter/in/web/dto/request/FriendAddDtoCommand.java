package transfer.banking.server.domain.friendship.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendAddDtoCommand {

  private final Long memberId;
  private final String friendName;
  private final String friendPhoneNumber;

  @Builder
  public FriendAddDtoCommand(Long memberId, String friendName, String friendPhoneNumber) {
    this.memberId = memberId;
    this.friendName = friendName;
    this.friendPhoneNumber = friendPhoneNumber;
  }

}
