package transfer.banking.server.domain.member.dto.response;

import java.util.Objects;
import lombok.Getter;
import transfer.banking.server.domain.member.entity.Member;

/**
 * 회원 정보 응답 DTO
 */
@Getter
public class MemberInfoDto {

  /**
   * 멤버 고유 번호
   */
  private Long id;

  /**
   * 멤버 이름
   */
  private String name;

  /**
   * 멤버 아이디
   */
  private String username;

  /**
   * 멤버 이메일
   */
  private String email;

  /**
   * 멤버 전화번호
   */
  private String phoneNumber;

  public MemberInfoDto(Member member) {
    this.id = member.getId();
    this.name = member.getName();
    this.username = member.getUsername();
    this.email = member.getEmail();
    this.phoneNumber = member.getPhoneNumber();
  }


  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MemberInfoDto)) {
      return false;
    }
    MemberInfoDto other = (MemberInfoDto) obj;
    return Objects.equals(id, other.id)
        && Objects.equals(username, other.username)
        && Objects.equals(email, other.email)
        && Objects.equals(name, other.name)
        && Objects.equals(phoneNumber, other.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, name, phoneNumber);
  }

}
