package transfer.banking.server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.member.dto.request.SignUpDto;
import transfer.banking.server.domain.member.dto.response.MemberInfoDto;
import transfer.banking.server.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {

  private final MemberService memberService;
  private final MemberValidatorService memberValidatorService;

  /**
   * 회원가입
   */
  public MemberInfoDto signUp(SignUpDto signUpDto) {
    memberValidatorService.validate(signUpDto);
    Member member = signUpDto.toEntity();
    memberService.signUp(member);
    return new MemberInfoDto(member);
  }

}
