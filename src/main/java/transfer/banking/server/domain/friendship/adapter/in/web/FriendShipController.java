package transfer.banking.server.domain.friendship.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDto;
import transfer.banking.server.domain.friendship.adapter.in.web.dto.request.FriendAddDtoCommand;
import transfer.banking.server.domain.friendship.application.port.in.FriendAddUseCase;
import transfer.banking.server.global.response.MessageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendShipController {

  private final FriendAddUseCase friendAddUseCase;

  @PostMapping()
  public ResponseEntity<MessageResponse> addFriend(@RequestBody FriendAddDto friendAddDto) {
    FriendAddDtoCommand command = new FriendAddDtoCommand(friendAddDto);
    friendAddUseCase.addFriend(command);
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "친구 추가 성공"), HttpStatus.OK);
  }

}
