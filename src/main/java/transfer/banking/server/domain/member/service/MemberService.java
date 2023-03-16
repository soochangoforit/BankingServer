package transfer.banking.server.domain.member.service;

import static transfer.banking.server.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.member.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.member.repository.MemberRepository;

/**
 * 멤버 순수 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 회원가입
   *
   * @return 회원
   */
  @Transactional
  public Member signUp(Member member) {
    return memberRepository.save(member);
  }

  /**
   * 회원 조회
   *
   * @param memberId 회원 ID
   * @return 회원
   */
  @Transactional(readOnly = true)
  public Member findMemberById(Long memberId) {
    log.info("memberId 를 통해서 멤버를 조회합니다. memberId: {}", memberId);
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
  }
}
