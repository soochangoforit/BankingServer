package transfer.banking.server.domain.friendship.application.port.in;

import java.util.List;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.MemberIdCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsAccountCommand;

/**
 * 친구 계좌 조회 UseCase
 * 입력으로 Command 객체를 받고, Command 객체를 반환한다.
 */
public interface FriendSearchUseCase {

  List<MyFriendsAccountCommand> searchMyFriends(MemberIdCommand command);
}
