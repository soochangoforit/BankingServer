package transfer.banking.server.domain.memberaccount.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;

public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {

  @Query("select ma.member from MemberAccount ma join ma.member m join ma.account ac where m.name = :friendName and ac.accountNumber = :friendAccountNumber")
  Optional<Member> findFriendByNameAndAccountNumber(@Param("friendName") String friendName, @Param("friendAccountNumber") String friendAccountNumber);
}
