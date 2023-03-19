package transfer.banking.server.domain.member.adapter.in.web.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청 DTO
 */
@Getter
@NoArgsConstructor
public class SignUpDto {

  /**
   * 멤버 이름
   */
  private String name;

  /**
   * 멤버 아이디
   */
  private String username;

  /**
   * 멤버 비밀번호
   */
  private String password;

  /**
   * 멤버 이메일
   */
  private String email;

  /**
   * 멤버 전화번호
   */
  private String phoneNumber;


  /**
   * SignUpDto 를 SignUpDtoCommand 로 변환한다.
   * 외부 Dto 를 내부 Command 로 변환하는 이유는 외부 Dto 는 변화에 민감하기 때문이다.
   * @return SignUpDtoCommand
   */
  public SignUpDtoCommand toCommand() {
    return SignUpDtoCommand.builder()
        .name(this.name)
        .username(this.username)
        .password(this.password)
        .email(this.email)
        .phoneNumber(this.phoneNumber)
        .build();
  }
}
