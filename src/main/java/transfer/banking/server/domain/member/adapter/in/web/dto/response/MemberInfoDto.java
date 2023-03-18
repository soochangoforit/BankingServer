package transfer.banking.server.domain.member.adapter.in.web.dto.response;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;


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

  @Builder
  public MemberInfoDto(Long id, String name, String username, String email, String phoneNumber) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public MemberInfoDto(MemberInfoDtoCommand memberInfoDtoCommand) {
    this.id = memberInfoDtoCommand.getId();
    this.name = memberInfoDtoCommand.getName();
    this.username = memberInfoDtoCommand.getUsername();
    this.email = memberInfoDtoCommand.getEmail();
    this.phoneNumber = memberInfoDtoCommand.getPhoneNumber();
  }

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
