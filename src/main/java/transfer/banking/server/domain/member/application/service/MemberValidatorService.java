package transfer.banking.server.domain.member.application.service;

import static transfer.banking.server.global.exception.ErrorCode.EMAIL_DUPLICATION;
import static transfer.banking.server.global.exception.ErrorCode.PHONE_DUPLICATION;
import static transfer.banking.server.global.exception.ErrorCode.USERNAME_DUPLICATION;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.member.adapter.out.persistence.repository.MemberRepositoryPort;
import transfer.banking.server.domain.member.application.exception.DuplicateMemberException;
import transfer.banking.server.domain.member.domain.MemberDomain;


/**
 * 회원가입 시 중복된 필드가 있는지 검증하는 서비스. (중간 복합 서비스)
 *
 * 필드 값으로 Repository 접근을 위한 Port Interface 를 사용한다.
 * 파라미터 값으로 Domain 을 사용한다.
 * 응답 값으로 Domain 을 사용한다.
 */
@Service
@RequiredArgsConstructor
public class MemberValidatorService {

  private final MemberService memberService;

  /**
   * 회원가입 시 중복된 필드가 있는지 검증.
   */
  public void validate(MemberDomain memberDomain) {
    if (memberService.existsByUsername(memberDomain.getUsername())) {
      throw new DuplicateMemberException(USERNAME_DUPLICATION);
    }

    if (memberService.existsByEmail(memberDomain.getEmail())) {
      throw new DuplicateMemberException(EMAIL_DUPLICATION);
    }

    if (memberService.existsByPhoneNumber(memberDomain.getPhoneNumber())) {
      throw new DuplicateMemberException(PHONE_DUPLICATION);
    }
  }


}
