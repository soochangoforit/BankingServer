package transfer.banking.server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 회원가입
   * @return
   */
  @Transactional
  public Member signUp(Member member) {
    return memberRepository.save(member);
  }
}
