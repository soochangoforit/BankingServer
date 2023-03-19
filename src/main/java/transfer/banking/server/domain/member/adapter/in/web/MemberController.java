package transfer.banking.server.domain.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.member.adapter.in.web.dto.response.MemberInfoDtoCommand;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDtoCommand;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDto;
import transfer.banking.server.domain.member.adapter.in.web.dto.response.MemberInfoDto;
import transfer.banking.server.domain.member.application.port.in.MemberSignUpUseCase;
import transfer.banking.server.global.response.DataResponse;

/**
 * 회원 컨트롤러
 * => Controller 가 가지는 책임
 * 1. HTTP 요청을 자바 객체로 매핑
 * 2. 권한 검사
 * 3. 입력 유효성 검증
 * 4. 입력을 유스케이스의 입력 모델(Command) 로 매핑
 * 5. 유스케이스 호출
 * 6. 유스케이스의 출력을 HTTP Dto 로 매핑
 * 7. HTTP 응답을 반환
 * [정리]
 * 필드 값으로 행동하길 원하는 interface 인 유스케이스를 주입 받는다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberSignUpUseCase memberSignUpUseCase;

  /**
   * 회원가입
   * @param signUpDto 회원가입 정보
   * @return 회원가입 결과
   */
  @PostMapping("/sign-up")
  public ResponseEntity<DataResponse<MemberInfoDto>> signUp(@RequestBody SignUpDto signUpDto) {

    SignUpDtoCommand signUpDtoCommand = signUpDto.toCommand();

    MemberInfoDtoCommand resCommand = memberSignUpUseCase.signUp(signUpDtoCommand);

    MemberInfoDto memberInfoDto = new MemberInfoDto(resCommand);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "회원가입 성공", memberInfoDto), HttpStatus.OK);
  }

}
