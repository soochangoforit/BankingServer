package transfer.banking.server.domain.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDto;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDtoCommand;
import transfer.banking.server.domain.member.adapter.in.web.dto.response.MemberInfoDtoCommand;
import transfer.banking.server.domain.member.application.mapper.MemberMapper;
import transfer.banking.server.domain.member.application.port.in.MemberSignUpUseCase;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 회원가입 복합 서비스 (유스케이스의 구현체)
 *
 * 핵심 비지니스 로직의 시작 지점이다.
 * 내부적으로 하위 순수 서비스들과 협력하고자 한다.
 */
@Service
@RequiredArgsConstructor
public class MemberSignUpService implements MemberSignUpUseCase {

  private final MemberService memberService;
  private final MemberValidatorService memberValidatorService;

  @Override
  public MemberInfoDtoCommand signUp(SignUpDtoCommand command) {
    MemberDomain domain = command.toMemberDomain();

    memberValidatorService.validate(domain);
    MemberDomain memberDomain = memberService.signUp(domain);

    return new MemberInfoDtoCommand(memberDomain);
  }
}
