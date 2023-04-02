package transfer.banking.server.domain.friendship.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.MemberIdCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsInfoCommand;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.member.domain.MemberDomain;

@ExtendWith(MockitoExtension.class)
class FriendSearchServiceTest {

  @Mock
  private FriendShipService friendShipService;
  @Mock
  private MemberService memberService;

  @InjectMocks
  private FriendSearchService friendSearchService;

  @Test
  @DisplayName("친구 검색 성공")
  void searchMyFriends() {
    // given
    long memberId = 1L;
    List<Long> friendIds = Arrays.asList(2L, 3L);
    List<MemberDomain> friendDomains =
        Arrays.asList(MemberDomain.builder().id(2L).build(), MemberDomain.builder().id(3L).build());

    given(friendShipService.searchMyFriendsId(memberId)).willReturn(friendIds);
    given(memberService.findMemberByIds(friendIds)).willReturn(friendDomains);

    // when
    List<MyFriendsInfoCommand> result = friendSearchService.searchMyFriends(
        new MemberIdCommand(memberId));

    // then
    assertThat(result).hasSize(friendDomains.size());

    for (int i = 0; i < friendDomains.size(); i++) {
      assertThat(result.get(i).getFriendId()).isEqualTo(friendDomains.get(i).getId());
    }
  }


}