package transfer.banking.server.domain.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDtoCommand;
import transfer.banking.server.domain.member.adapter.in.web.dto.response.MemberInfoDtoCommand;
import transfer.banking.server.domain.member.application.port.in.MemberSignUpUseCase;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 회원가입 복합 서비스 (유스케이스의 구현체)
 * 핵심 비지니스 로직의 시작 지점이다.
 * 내부적으로 하위 순수 서비스들과 협력하고자 한다.
 * 하위 순수 서비스도 interface 를 통해서 협력할 수 있다. (ex 외부 결제 시스템 사용시)
 * 하지만, 현 프로젝트 단계에서는 외부 시스템에 대한 주입은 없기 때문에
 * 구체 class 인 하위 서비스와 협력하고자 한다.
 *
 * [의문점]
 * 외부 Dto 를 Command 객체로 변환한건 좋지만,
 * Command 객체를 다시 Domain 객체로 바꿔서 사용하는게 정말 괜찮을까?
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
