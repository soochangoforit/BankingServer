package transfer.banking.server.domain.member.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.member.domain.MemberDomain;


@Getter
public class SignUpDtoCommand {

  /**
   * 멤버 이름
   */
  private final String name;

  /**
   * 멤버 아이디
   */
  private final String username;

  /**
   * 멤버 비밀번호
   */
  private final String password;

  /**
   * 멤버 이메일
   */
  private final String email;

  /**
   * 멤버 전화번호
   */
  private final String phoneNumber;

  @Builder
  public SignUpDtoCommand(String name, String username, String password, String email,
      String phoneNumber) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  /**
   * SignUpDtoCommand 를 MemberDomain 으로 변환한다.
   * @return MemberDomain
   */
  public MemberDomain toMemberDomain() {
    return MemberDomain.builder()
        .name(this.name)
        .username(this.username)
        .password(this.password)
        .email(this.email)
        .phoneNumber(this.phoneNumber)
        .build();
  }
}
