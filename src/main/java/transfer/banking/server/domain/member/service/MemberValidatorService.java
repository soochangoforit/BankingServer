package transfer.banking.server.domain.member.service;

import static transfer.banking.server.global.exception.ErrorCode.EMAIL_DUPLICATION;
import static transfer.banking.server.global.exception.ErrorCode.PHONE_DUPLICATION;
import static transfer.banking.server.global.exception.ErrorCode.USERNAME_DUPLICATION;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.member.dto.request.SignUpDto;
import transfer.banking.server.domain.member.exception.DuplicateMemberException;
import transfer.banking.server.domain.member.repository.MemberRepository;

/**
 * 회원가입 시 중복된 필드가 있는지 검증하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class MemberValidatorService {

  private final MemberRepository memberRepository;

  /**
   * 회원가입 시 중복된 필드가 있는지 검증.
   * @param signUpDto 회원가입 요청 DTO
   */
  public void validate(SignUpDto signUpDto) {
    if (memberRepository.existsByUsername(signUpDto.getUsername())) {
      throw new DuplicateMemberException(USERNAME_DUPLICATION);
    }

    if (memberRepository.existsByEmail(signUpDto.getEmail())) {
      throw new DuplicateMemberException(EMAIL_DUPLICATION);
    }

    if (memberRepository.existsByPhoneNumber(signUpDto.getPhoneNumber())) {
      throw new DuplicateMemberException(PHONE_DUPLICATION);
    }
  }
}
