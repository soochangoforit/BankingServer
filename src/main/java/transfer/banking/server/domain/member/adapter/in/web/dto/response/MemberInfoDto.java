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

  /**
   * 비지니스 로직을 수행한 후 반환되는 Command 객체를 DTO 로 변환하는 생성자
   * @param memberInfoDtoCommand 비지니스 로직을 수행한 후 반환되는 Command 객체
   */
  public MemberInfoDto(MemberInfoDtoCommand memberInfoDtoCommand) {
    this.id = memberInfoDtoCommand.getId();
    this.name = memberInfoDtoCommand.getName();
    this.username = memberInfoDtoCommand.getUsername();
    this.email = memberInfoDtoCommand.getEmail();
    this.phoneNumber = memberInfoDtoCommand.getPhoneNumber();
  }


}
