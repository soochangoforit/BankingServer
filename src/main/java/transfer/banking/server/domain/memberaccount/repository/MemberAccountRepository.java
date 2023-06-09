package transfer.banking.server.domain.memberaccount.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;

public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {

  @Query("select ma from MemberAccount ma join fetch ma.member m join fetch ma.account ac where ac.bank = :bank and ac.accountNumber = :accountNumber")
  Optional<MemberAccount> findFriendAccountByNumAndBank(@Param("accountNumber") String accountNumber, @Param("bank") Bank bank);

  @Query("select ma from MemberAccount ma  where ma.member.id = :memberId and ma.account.id = :accountId")
  Optional<MemberAccount> checkIfMemberOwnsAccount(@Param("memberId") Long memberId, @Param("accountId") Long accountId);
}
