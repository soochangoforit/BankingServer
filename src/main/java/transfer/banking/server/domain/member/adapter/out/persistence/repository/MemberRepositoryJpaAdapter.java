package transfer.banking.server.domain.member.adapter.out.persistence.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.application.port.out.MemberRepositoryPort;
import transfer.banking.server.domain.member.application.mapper.MemberMapper;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 외무 DB 에 접근하는 Jpa Repository Adapter.
 *
 * 필드 값으로 JpaRepository 를 extends 한 Repository 를 사용한다.
 * 트랜잭션 처리를 위해 @Transactional 어노테이션을 사용한다.
 * 실질적으로 트랜잭션이 시작하는 곳이다.
 *
 * 파라미터 값으로 Domain 을 사용한다.
 * 응답 값으로 Domain 을 사용한다.
 * 내부적으로 Jpa, Redis, MongoDB 등 성격에 따른 Entity 혹은 primitive type 으로 변환하여 영속화 하거나 조회한다.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryJpaAdapter implements MemberRepositoryPort {

  private final MemberJpaRepository memberJpaRepository;
  private final MemberMapper memberMapper;

  @Override
  @Transactional
  public MemberDomain save(MemberDomain domain) {
    Member memberEntity = memberMapper.toJpaEntity(domain);
    memberJpaRepository.save(memberEntity);
    return memberMapper.toDomain(memberEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsername(String username) {
    return memberJpaRepository.existsByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByEmail(String email) {
    return memberJpaRepository.existsByEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByPhoneNumber(String phoneNumber) {
    return memberJpaRepository.existsByPhoneNumber(phoneNumber);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<MemberDomain> findById(Long memberId) {
    Optional<Member> optionalMember = memberJpaRepository.findById(memberId);
    return memberMapper.toOptionalDomain(optionalMember);
  }


}
