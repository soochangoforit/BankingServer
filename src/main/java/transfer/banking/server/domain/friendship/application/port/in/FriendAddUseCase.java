package transfer.banking.server.domain.friendship.application.port.in;

import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDtoCommand;

/**
 * 친구 추가 유스케이스
 * 입력 값으로 Command 객체를 받는다.
 * 응답 값으로 Command 객체를 반환한다.
 */
public interface FriendAddUseCase {

  void addFriend(FriendAddDtoCommand command);
}
