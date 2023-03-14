package transfer.banking.server.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import transfer.banking.server.domain.member.entity.Member;

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

  public Member toEntity() {
    return Member.builder()
        .name(name)
        .username(username)
        .password(password)
        .email(email)
        .phoneNumber(phoneNumber)
        .build();
  }

  @Builder
  public SignUpDto(String name, String username, String password, String email, String phoneNumber) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }
}
