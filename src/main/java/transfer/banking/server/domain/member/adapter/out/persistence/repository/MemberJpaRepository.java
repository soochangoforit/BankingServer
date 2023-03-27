package transfer.banking.server.domain.member.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {


  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  Optional<Member> findByUsername(String username);

  @Query("select m from Member m where m.id in :myFriendIds")
  List<Member> findByIdIn(@Param("myFriendIds") List<Long> myFriendIds);
}
