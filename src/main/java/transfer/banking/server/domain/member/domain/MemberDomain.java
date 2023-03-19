package transfer.banking.server.domain.member.domain;


import lombok.Builder;
import lombok.Getter;

/**
 * Member 정보를 담고 있는 도메인 객체 도메인 객체는 Entity 와는 다른 개념이다.
 * Entity 는 DB 에 저장되는 객체이고, 도메인 객체는 DB 에 저장되지 않는 객체이다.
 * Domain 객체는 비지니스 로직을 담고 있다.
 */
@Getter
public class MemberDomain {

  private Long id;
  private String username;
  private String password;
  private String email;
  private String name;
  private String phoneNumber;

  @Builder
  public MemberDomain(Long id, String username, String password, String email, String name,
      String phoneNumber) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

}
