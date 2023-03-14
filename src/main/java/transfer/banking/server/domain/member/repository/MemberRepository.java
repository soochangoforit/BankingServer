package transfer.banking.server.domain.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import transfer.banking.server.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  Optional<Member> findByUsername(String username);
}
