package transfer.banking.server.domain.friendship.adapter.in.web.dto.request;

import lombok.Getter;

@Getter
public class MemberIdCommand {

  private final Long memberId;

  public MemberIdCommand(Long memberId) {
    this.memberId = memberId;
  }

}
