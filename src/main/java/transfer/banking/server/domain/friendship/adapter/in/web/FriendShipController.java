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
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsInfoCommand;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.response.MyFriendsInfoDto;
import transfer.banking.server.domain.friendship.application.port.in.FriendAddUseCase;
import transfer.banking.server.domain.friendship.application.port.in.FriendSearchUseCase;
import transfer.banking.server.global.response.DataResponse;
import transfer.banking.server.global.response.MessageResponse;

/**
 * 친구 계좌 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendShipController {

  private final FriendAddUseCase friendAddUseCase;
  private final FriendSearchUseCase friendSearchUseCase;

  /**
   * 친구 추가
   *
   * @param friendAddDto 친구 추가 정보
   * @return 친구 추가 결과 메시지
   */
  @PostMapping()
  public ResponseEntity<MessageResponse> addFriend(@RequestBody FriendAddDto friendAddDto) {
    FriendAddDtoCommand friendAddDtoCommand = friendAddDto.toCommand();
    friendAddUseCase.addFriend(friendAddDtoCommand);
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "친구 추가 성공"), HttpStatus.OK);
  }

  /**
   * 내가 등록한 친구 목록 조회
   *
   * @param memberId 회원 ID
   * @return 친구 목록 응답
   */
  @GetMapping("/my-friends")
  public ResponseEntity<DataResponse<List<MyFriendsInfoDto>>> searchMyFriends(@RequestParam("memberId") Long memberId) {
    MemberIdCommand command = new MemberIdCommand(memberId);
    List<MyFriendsInfoCommand> commands = friendSearchUseCase.searchMyFriends(command);
    List<MyFriendsInfoDto> responseDtoList = MyFriendsInfoDto.toDtos(commands);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "친구 목록 조회 성공", responseDtoList), HttpStatus.OK);
  }


}
