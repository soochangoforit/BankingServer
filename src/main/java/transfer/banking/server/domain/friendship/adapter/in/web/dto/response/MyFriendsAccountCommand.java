package transfer.banking.server.domain.friendship.adapter.in.web.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

@Getter
public class MyFriendsAccountCommand {

  private final Long friendId;
  private final String friendName;

  private final Long friendAccountId;
  private final String friendAccountNumber;
  private final String friendAccountBank;

  @Builder
  public MyFriendsAccountCommand(Long friendId, String friendName, Long friendAccountId,
      String friendAccountNumber, String friendAccountBank) {
    this.friendId = friendId;
    this.friendName = friendName;
    this.friendAccountId = friendAccountId;
    this.friendAccountNumber = friendAccountNumber;
    this.friendAccountBank = friendAccountBank;
  }

  public static List<MyFriendsAccountCommand> toCommands(List<MemberAccountDomain> domains) {
    return domains.stream()
        .map(MyFriendsAccountCommand::toCommand)
        .toList();
  }

  private static MyFriendsAccountCommand toCommand(MemberAccountDomain memberAccountDomain) {
    return MyFriendsAccountCommand.builder()
        .friendId(memberAccountDomain.getMember().getId())
        .friendName(memberAccountDomain.getMember().getName())
        .friendAccountId(memberAccountDomain.getAccount().getId())
        .friendAccountNumber(memberAccountDomain.getAccount().getAccountNumber())
        .friendAccountBank(memberAccountDomain.getAccount().getBank().name())
        .build();
  }
}
