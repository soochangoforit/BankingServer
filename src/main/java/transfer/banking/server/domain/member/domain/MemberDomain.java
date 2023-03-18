package transfer.banking.server.domain.member.domain;


import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDomain {

  private Long id;
  private String username;
  private String password;
  private String email;
  private String name;
  private String phoneNumber;

  @Builder
  public MemberDomain(Long id, String username, String password, String email, String name, String phoneNumber) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

}
