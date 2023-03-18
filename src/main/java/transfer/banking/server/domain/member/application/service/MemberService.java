package transfer.banking.server.domain.member.application.service;

import static transfer.banking.server.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.member.adapter.out.persistence.repository.MemberRepositoryPort;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 멤버 순수 서비스, 필드 값으로 Repository 접근을 위한 Port Interface 를 사용한다.
 *
 * 트랜잭션을 아직 시작하지 않는다.
 * DB 접근을 위한 Adapter Class 에서 트랜잭션을 시작한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepositoryPort memberRepository;


  public MemberDomain signUp(MemberDomain domain) {
    return memberRepository.save(domain);
  }

  public boolean existsByUsername(String username) {
    return memberRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  public boolean existsByPhoneNumber(String phoneNumber) {
    return memberRepository.existsByPhoneNumber(phoneNumber);
  }


  public MemberDomain findMemberById(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
  }
}
