package transfer.banking.server.domain.member.application.mapper;

import org.springframework.stereotype.Component;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDtoCommand;
import transfer.banking.server.domain.member.adapter.in.web.dto.response.MemberInfoDtoCommand;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * Domain 객체를 Entity 객체로 변환하는 Mapper
 * Entity 객체를 Domain 객체로 변환하는 Mapper
 */
@Component
public class MemberMapper {

  /**
   * Command 객체를 Entity 객체로 변환
   */
  public Member toJpaEntity(MemberDomain command) {
    return Member.builder()
        .name(command.getName())
        .username(command.getUsername())
        .email(command.getEmail())
        .password(command.getPassword())
        .phoneNumber(command.getPhoneNumber())
        .build();
  }

  /**
   * Entity 객체를 Domain 객체로 변환
   */
  public MemberDomain toDomain(Member memberJpaEntity) {
    return MemberDomain.builder()
        .id(memberJpaEntity.getId())
        .username(memberJpaEntity.getUsername())
        .name(memberJpaEntity.getName())
        .email(memberJpaEntity.getEmail())
        .password(memberJpaEntity.getPassword())
        .phoneNumber(memberJpaEntity.getPhoneNumber())
        .build();
  }

}