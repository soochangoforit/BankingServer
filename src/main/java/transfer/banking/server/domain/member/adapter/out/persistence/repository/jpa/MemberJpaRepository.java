package transfer.banking.server.domain.member.adapter.out.persistence.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {


  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  Optional<Member> findByUsername(String username);
}
