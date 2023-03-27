package transfer.banking.server.domain.friendship.adapter.in.web.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 나의 친구 목록 조회 응답 DTO Command 객체
 */
@Getter
public class MyFriendsInfoCommand {

  private final Long friendId;
  private final String friendName;

  private final String friendEmail;
  private final String friendPhoneNumber;

  @Builder
  public MyFriendsInfoCommand(Long friendId, String friendName, String friendEmail,
      String friendPhoneNumber) {
    this.friendId = friendId;
    this.friendName = friendName;
    this.friendEmail = friendEmail;
    this.friendPhoneNumber = friendPhoneNumber;
  }

  public static List<MyFriendsInfoCommand> toCommands(List<MemberDomain> friendDomains) {
    return friendDomains.stream()
        .map(MyFriendsInfoCommand::toCommand)
        .toList();
  }

  private static MyFriendsInfoCommand toCommand(MemberDomain memberDomain) {
    return MyFriendsInfoCommand.builder()
        .friendId(memberDomain.getId())
        .friendName(memberDomain.getName())
        .friendEmail(memberDomain.getEmail())
        .friendPhoneNumber(memberDomain.getPhoneNumber())
        .build();
  }
}
