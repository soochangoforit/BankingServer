package transfer.banking.server.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.member.dto.request.SignUpDto;
import transfer.banking.server.domain.member.dto.response.MemberInfoDto;
import transfer.banking.server.domain.member.service.MemberSignUpService;
import transfer.banking.server.global.response.DataResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberSignUpService memberSignUpService;

  /**
   * 회원가입
   */
  @PostMapping("/sign-up")
  public ResponseEntity<DataResponse<MemberInfoDto>> signUp(@RequestBody SignUpDto signUpDto) {
    MemberInfoDto memberInfoDto = memberSignUpService.signUp(signUpDto);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "회원가입 성공", memberInfoDto), HttpStatus.OK);
  }

}
