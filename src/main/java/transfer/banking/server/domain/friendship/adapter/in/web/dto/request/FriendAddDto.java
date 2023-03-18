package transfer.banking.server.domain.friendship.adapter.in.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

/**
 * 친구 추가 요청 DTO
 */
@Getter
@NoArgsConstructor
public class FriendAddDto {

  private Long memberId;
  private Bank friendAccountBank;
  private String friendAccountNumber;

}
