package transfer.banking.server.domain.friendship.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.application.exception.CanTransferWithOnlyFriend;
import transfer.banking.server.domain.friendship.application.exception.AlreadyFriendException;
import transfer.banking.server.domain.friendship.application.port.out.FriendShipRepositoryPort;

@ExtendWith(MockitoExtension.class)
class FriendShipServiceTest {

  @Mock
  private FriendShipRepositoryPort friendShipRepository;

  @InjectMocks
  private FriendShipService friendShipService;

  @Test
  @DisplayName("친구 관계가 존재하는지 확인한다. (정상적인 경우)")
  void checkIfFriendShipExists() {
    // given
    Long memberId = 1L;
    Long friendId = 2L;

    // when
    when(friendShipRepository.existsByMemberIdAndFriendId(memberId, friendId)).thenReturn(false);

    // then
    assertDoesNotThrow(() -> friendShipService.checkIfFriendShipExists(memberId, friendId));
    verify(friendShipRepository, times(1)).existsByMemberIdAndFriendId(memberId, friendId);
  }

  @Test
  @DisplayName("친구 관계가 존재하는지 확인한다. (비정상적인 경우)")
  void checkIfFriendShipExistsException() {
    // given
    Long memberId = 1L;
    Long friendId = 2L;

    // when
    when(friendShipRepository.existsByMemberIdAndFriendId(memberId, friendId)).thenReturn(true);

    // then
    assertThrows(AlreadyFriendException.class, () -> friendShipService.checkIfFriendShipExists(memberId, friendId));
    verify(friendShipRepository, times(1)).existsByMemberIdAndFriendId(memberId, friendId);
  }

  @Test
  @DisplayName("친구 추가 성공")
  void saveFriendShip() {
    // given
    Long memberId = 1L;
    Long friendId = 2L;

    // when
    friendShipService.saveFriendShip(memberId, friendId);

    // then
    verify(friendShipRepository, times(1)).save(memberId, friendId);
  }

  @Test
  @DisplayName("자신의 친구 아이디 목록 조회")
  void searchMyFriendsId() {
    // given
    Long memberId = 1L;
    List<Long> expectedFriendIds = Arrays.asList(2L, 3L, 4L);
    when(friendShipRepository.searchMyFriends(memberId)).thenReturn(expectedFriendIds);

    // when
    List<Long> actualFriendIds = friendShipService.searchMyFriendsId(memberId);

    // then
    assertEquals(expectedFriendIds, actualFriendIds);
    verify(friendShipRepository, times(1)).searchMyFriends(memberId);
  }

  @Test
  @DisplayName("친구 관계 인지 확인 (친구 관계인 경우)")
  void canTransferOnlyWithFriend() {
    // given
    Long memberId = 1L;
    Long friendId = 2L;
    when(friendShipRepository.existsByMemberIdAndFriendId(memberId, friendId)).thenReturn(true);

    // when & then
    assertDoesNotThrow(() -> friendShipService.canTransferOnlyWithFriend(memberId, friendId));
  }

  @Test
  @DisplayName("계좌 이체를 위해선 친구 관계 인지 확인 (친구 관계가 아닌 경우)")
  void canTransferOnlyWithFriendException() {
    // given
    Long memberId = 1L;
    Long friendId = 2L;
    when(friendShipRepository.existsByMemberIdAndFriendId(memberId, friendId)).thenReturn(false);

    // when & then
    assertThrows(CanTransferWithOnlyFriend.class, () -> friendShipService.canTransferOnlyWithFriend(memberId, friendId));
  }

}