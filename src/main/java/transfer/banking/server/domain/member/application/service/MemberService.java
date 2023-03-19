package transfer.banking.server.domain.member.application.service;

import static transfer.banking.server.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.member.application.port.out.MemberRepositoryPort;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 멤버 순수 서비스, 필드 값으로 Repository 접근을 위한 Port Interface 를 사용한다.
 * 트랜잭션을 아직 시작하지 않는다.
 * DB 접근을 위한 Repository Adapter Class 에서 트랜잭션을 시작한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepositoryPort memberRepository;

  /**
   * 회원가입
   *
   * @param domain 회원가입 하고자 하는 회원 도메인 객체
   * @return 회원가입이 성공된 회원 도메인 객체
   */
  public MemberDomain signUp(MemberDomain domain) {
    return memberRepository.save(domain);
  }

  /**
   * 회원가입 시, 중복된 아이디가 있는지 검증
   *
   * @param username 회원가입 하고자 하는 회원 아이디
   * @return 중복된 아이디가 있으면 true, 없으면 false
   */
  public boolean existsByUsername(String username) {
    return memberRepository.existsByUsername(username);
  }

  /**
   * 회원가입 시, 중복된 이메일이 있는지 검증
   *
   * @param email 회원가입 하고자 하는 회원 이메일
   * @return 중복된 이메일이 있으면 true, 없으면 false
   */
  public boolean existsByEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  /**
   * 회원가입 시, 중복된 전화번호가 있는지 검증
   *
   * @param phoneNumber 회원가입 하고자 하는 회원 전화번호
   * @return 중복된 전화번호가 있으면 true, 없으면 false
   */
  public boolean existsByPhoneNumber(String phoneNumber) {
    return memberRepository.existsByPhoneNumber(phoneNumber);
  }


  /**
   * 회원 아이디로 회원을 조회한다.
   * 없으면 MemberNotFoundException 예외를 발생시킨다.
   *
   * @param memberId 회원 아이디
   * @return 회원 도메인 객체
   */
  public MemberDomain findMemberById(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
  }
}
