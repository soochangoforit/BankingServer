package transfer.banking.server.domain.friendship.adapter.in.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDto;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDtoCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.MemberIdCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsAccountCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsAccountDto;
import transfer.banking.server.domain.friendship.application.port.in.FriendAddUseCase;
import transfer.banking.server.domain.friendship.application.port.in.FriendSearchUseCase;
import transfer.banking.server.global.response.DataResponse;
import transfer.banking.server.global.response.MessageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendShipController {

  private final FriendAddUseCase friendAddUseCase;
  private final FriendSearchUseCase friendSearchUseCase;

  @PostMapping()
  public ResponseEntity<MessageResponse> addFriend(@RequestBody FriendAddDto friendAddDto) {
    FriendAddDtoCommand command = new FriendAddDtoCommand(friendAddDto);
    friendAddUseCase.addFriend(command);
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "친구 추가 성공"), HttpStatus.OK);
  }

  /**
   * 내가 등록한 친구 목록 조회
   */
  @GetMapping("/my-friends")
  public ResponseEntity<DataResponse<List<MyFriendsAccountDto>>> searchMyFriends(@RequestParam("memberId") Long memberId) {
    MemberIdCommand command = new MemberIdCommand(memberId);
    List<MyFriendsAccountCommand> commands = friendSearchUseCase.searchMyFriends(command);
    List<MyFriendsAccountDto> responseDtoList = MyFriendsAccountDto.toDtos(commands);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "친구 목록 조회 성공", responseDtoList), HttpStatus.OK);
  }


}
