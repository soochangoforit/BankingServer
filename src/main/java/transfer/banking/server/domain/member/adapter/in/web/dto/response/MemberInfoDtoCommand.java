package transfer.banking.server.domain.member.adapter.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.member.domain.MemberDomain;

@Getter
public class MemberInfoDtoCommand {

  /**
   * 멤버 고유 번호
   */
  private final Long id;

  /**
   * 멤버 이름
   */
  private final String name;

  /**
   * 멤버 아이디
   */
  private final String username;

  /**
   * 멤버 이메일
   */
  private final String email;

  /**
   * 멤버 전화번호
   */
  private final String phoneNumber;


  public MemberInfoDtoCommand(MemberDomain memberDomain) {
    this.id = memberDomain.getId();
    this.name = memberDomain.getName();
    this.username = memberDomain.getUsername();
    this.email = memberDomain.getEmail();
    this.phoneNumber = memberDomain.getPhoneNumber();
  }

}
