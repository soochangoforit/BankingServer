package transfer.banking.server.domain.friendship.adapter.in.web.dto.response;

import java.util.List;
import lombok.Getter;

/**
 * 나의 친구 목록 조회 응답 DTO
 */
@Getter
public class MyFriendsInfoDto {

  private final Long friendId;
  private final String friendName;
  private final String friendEmail;
  private final String friendPhoneNumber;


  public MyFriendsInfoDto(Long friendId, String friendName, String friendEmail,
      String friendPhoneNumber) {
    this.friendId = friendId;
    this.friendName = friendName;
    this.friendEmail = friendEmail;
    this.friendPhoneNumber = friendPhoneNumber;
  }

  public static List<MyFriendsInfoDto> toDtos(List<MyFriendsInfoCommand> commands) {
    return commands.stream()
        .map(MyFriendsInfoDto::toDto)
        .toList();
  }

  private static MyFriendsInfoDto toDto(MyFriendsInfoCommand myFriendsInfoCommand) {
    return new MyFriendsInfoDto(
         myFriendsInfoCommand.getFriendId(),
          myFriendsInfoCommand.getFriendName(),
          myFriendsInfoCommand.getFriendEmail(),
          myFriendsInfoCommand.getFriendPhoneNumber()
    );
  }
}
