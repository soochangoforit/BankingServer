package transfer.banking.server.domain.member.adapter.out.persistence.repository;

import java.util.Optional;
import transfer.banking.server.domain.member.domain.MemberDomain;


/**
 * 순수 서비스와 외부 DB 를 연결하는 Repository 인터페이스
 *
 * 인터페이스화 함으로써 순수 서비스와 외부 DB 를 분리할 수 있다.
 * Data JPA 환경으로 변경할수도 있고, 다른 DB 환경으로 변경할 수도 있다.
 */
public interface MemberRepositoryPort {

  MemberDomain save(MemberDomain domain);


  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  Optional<MemberDomain> findById(Long memberId);
}
