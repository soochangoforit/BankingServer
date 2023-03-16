package transfer.banking.server.domain.friendship.dto.request;

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
  private String friendAccountNumber;

}
