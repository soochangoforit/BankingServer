package transfer.banking.server.domain.member.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.application.port.out.MemberRepositoryPort;
import transfer.banking.server.domain.member.application.mapper.MemberMapper;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 외무 DB 에 접근하는 Jpa Repository Adapter.
 * 필드 값으로 JpaRepository 를 extends 한 Repository 를 사용한다.
 * 트랜잭션 처리를 위해 @Transactional 어노테이션을 사용한다.
 * 실질적으로 트랜잭션이 시작하는 곳이다.
 * 파라미터 값으로 Domain 을 사용한다.
 * 응답 값으로 Domain 을 사용한다.
 * 요청값으로 Domain 객체를 받더라도
 * 내부적으로 Jpa, Redis, MongoDB 등 성격에 따른 {Entity 혹은 primitive type 으로 변환}하여 영속화 하거나 조회한다.
 * optional 를 포함한 domain 객체를 그대로 반환한다.
 * optional 에 대한 예외 처리는 상위 순수 서비스에서 한다.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryJpaAdapter implements MemberRepositoryPort {

  private final MemberJpaRepository memberJpaRepository;
  private final MemberMapper memberMapper;

  @Override
  public MemberDomain save(MemberDomain domain) {
    Member memberEntity = memberMapper.toJpaEntity(domain);
    log.info("member entity 를 저장합니다. member id = {}", memberEntity.getId());
    memberJpaRepository.save(memberEntity);
    return memberMapper.toDomain(memberEntity);
  }

  @Override
  public boolean existsByUsername(String username) {
    log.info("username = {} 의 존재 여부를 확인합니다.", username);
    return memberJpaRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    log.info("email = {} 의 존재 여부를 확인합니다.", email);
    return memberJpaRepository.existsByEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByPhoneNumber(String phoneNumber) {
    log.info("phoneNumber = {} 의 존재 여부를 확인합니다.", phoneNumber);
    return memberJpaRepository.existsByPhoneNumber(phoneNumber);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<MemberDomain> findById(Long memberId) {
    log.info("memberId = {} 의 존재 여부를 확인합니다.", memberId);
    Optional<Member> optionalMember = memberJpaRepository.findById(memberId);
    return optionalMember.map(memberMapper::toDomain);
  }

  @Override
  public List<MemberDomain> findByIds(List<Long> myFriendIds) {
    log.info("memberId = {} 인 member 들을 찾습니다.", myFriendIds);
    List<Member> memberEntities = memberJpaRepository.findByIdIn(myFriendIds);
    return memberMapper.toDomainList(memberEntities);
  }


}
