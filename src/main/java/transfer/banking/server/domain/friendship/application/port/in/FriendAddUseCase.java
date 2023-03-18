package transfer.banking.server.domain.friendship.application.port.in;

import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDtoCommand;

public interface FriendAddUseCase {

  void addFriend(FriendAddDtoCommand command);
}
