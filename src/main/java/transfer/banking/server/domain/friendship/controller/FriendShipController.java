package transfer.banking.server.domain.friendship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.friendship.dto.request.FriendAddDto;
import transfer.banking.server.domain.friendship.service.FriendAddService;
import transfer.banking.server.global.response.MessageResponse;

/**
 * 친구 관계 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendShipController {

  private final FriendAddService friendAddService;

  /**
   * 친구 추가
   *
   * @param friendAddDto 친구 추가 요청 DTO
   * @return 친구 추가 성공 메시지
   */
  @PostMapping()
  public ResponseEntity<MessageResponse> addFriend(@RequestBody FriendAddDto friendAddDto) {
    friendAddService.addFriend(friendAddDto);
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.CREATED, "친구 추가 성공"), HttpStatus.CREATED);
  }

}
