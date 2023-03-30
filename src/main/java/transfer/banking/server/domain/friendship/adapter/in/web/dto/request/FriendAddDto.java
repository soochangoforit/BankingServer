package transfer.banking.server.domain.friendship.adapter.in.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 친구 추가 요청 DTO
 */
@Getter
@NoArgsConstructor
public class FriendAddDto {

  private Long memberId;
  private String friendName;
  private String friendPhoneNumber;

  public FriendAddDtoCommand toCommand() {
    return FriendAddDtoCommand.builder()
        .memberId(this.memberId)
        .friendName(this.friendName)
        .friendPhoneNumber(this.friendPhoneNumber)
        .build();
  }
}
