package transfer.banking.server.domain.friendship.application.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDtoCommand;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.member.domain.MemberDomain;

@ExtendWith(MockitoExtension.class)
class FriendAddServiceTest {

  @Mock
  private FriendShipService friendShipService;

  @Mock
  private MemberService memberService;

  @InjectMocks
  private FriendAddService friendAddService;

  @Test
  @DisplayName("친구 추가 성공")
  void addFriend() {
    // given
    long myMemberId = 1L;
    String friendName = "홍길동";
    String friendPhoneNumber = "01011112222";
    FriendAddDtoCommand command = new FriendAddDtoCommand(myMemberId, friendName,
        friendPhoneNumber);

    long friendId = 2L;

    MemberDomain friendDomain = MemberDomain.builder()
        .id(friendId)
        .name(friendName)
        .phoneNumber(friendPhoneNumber)
        .build();

    // when
    when(memberService.findMemberByNameAndNumber(command.getFriendName(),
        command.getFriendPhoneNumber())).thenReturn(friendDomain);
    doNothing().when(friendShipService).saveFriendShip(command.getMemberId(), friendDomain.getId());

    friendAddService.addFriend(command);

    // then
    verify(memberService, times(1)).findMemberByNameAndNumber(command.getFriendName(),
        command.getFriendPhoneNumber());
    verify(friendShipService, times(1)).checkIfFriendShipExists(command.getMemberId(),
        friendDomain.getId());
    verify(friendShipService, times(1)).saveFriendShip(command.getMemberId(), friendDomain.getId());
  }
}