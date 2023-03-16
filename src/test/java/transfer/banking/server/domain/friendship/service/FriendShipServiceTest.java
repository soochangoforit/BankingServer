package transfer.banking.server.domain.friendship.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.friendship.entity.FriendShip;
import transfer.banking.server.domain.friendship.exception.AlreadyFriendException;
import transfer.banking.server.domain.friendship.repository.FriendShipRepository;
import transfer.banking.server.domain.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class FriendShipServiceTest {

  @Mock
  private FriendShipRepository friendShipRepository;

  @InjectMocks
  private FriendShipService friendShipService;

  private Member member;
  private Member friend;

  @BeforeEach
  void setUp() {
    member = new Member("username", "password", "email", "name", "phone");
    friend = new Member("username", "password", "email", "name", "phone");
  }


  @Test
  @DisplayName("친구 추가 성공")
  void addFriend() {
    FriendShip friendShip = new FriendShip(member.getId(), friend.getId());
    when(friendShipRepository.save(any(FriendShip.class))).thenReturn(friendShip);

    friendShipService.addFriend(member, friend);

    verify(friendShipRepository, times(1)).save(any(FriendShip.class));
  }

  @Test
  @DisplayName("친구 추가 실패 - 이미 친구인 경우")
  void checkAlreadyFriend() {
    when(friendShipRepository.findByMemberIdAndFriendId(member.getId(), friend.getId())).thenReturn(
        Optional.of(new FriendShip(member.getId(), friend.getId())));

    assertThrows(AlreadyFriendException.class,
        () -> friendShipService.checkAlreadyFriend(member, friend));
  }

  @Test
  @DisplayName("친구 추가 성공 - 아직 친구가 아닌 경우")
  public void testCheckAlreadyFriend_NotFriends() {
    when(friendShipRepository.findByMemberIdAndFriendId(member.getId(), friend.getId())).thenReturn(
        Optional.empty());
    assertDoesNotThrow(() -> friendShipService.checkAlreadyFriend(member, friend));
  }
}