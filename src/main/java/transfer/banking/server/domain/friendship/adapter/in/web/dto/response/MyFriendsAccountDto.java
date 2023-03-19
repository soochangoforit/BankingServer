package transfer.banking.server.domain.friendship.adapter.in.web.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class MyFriendsAccountDto {

  private final Long friendId;
  private final String friendName;
  private final Long friendAccountId;
  private final String friendAccountNumber;
  private final String friendAccountBank;

  public MyFriendsAccountDto(Long friendId, String friendName, Long friendAccountId,
      String friendAccountNumber, String friendAccountBank) {
    this.friendId = friendId;
    this.friendName = friendName;
    this.friendAccountId = friendAccountId;
    this.friendAccountNumber = friendAccountNumber;
    this.friendAccountBank = friendAccountBank;
  }

  public static List<MyFriendsAccountDto> toDtos(List<MyFriendsAccountCommand> commands) {
    return commands.stream()
        .map(MyFriendsAccountDto::toDto)
        .toList();
  }

  private static MyFriendsAccountDto toDto(MyFriendsAccountCommand myFriendsAccountCommand) {
    return new MyFriendsAccountDto(
        myFriendsAccountCommand.getFriendId(),
        myFriendsAccountCommand.getFriendName(),
        myFriendsAccountCommand.getFriendAccountId(),
        myFriendsAccountCommand.getFriendAccountNumber(),
        myFriendsAccountCommand.getFriendAccountBank()
    );
  }
}
