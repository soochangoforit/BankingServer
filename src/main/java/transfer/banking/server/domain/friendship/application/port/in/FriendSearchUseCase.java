package transfer.banking.server.domain.friendship.application.port.in;

import java.util.List;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.MemberIdCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsAccountCommand;

public interface FriendSearchUseCase {

  List<MyFriendsAccountCommand> searchMyFriends(MemberIdCommand command);
}
