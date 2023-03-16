package transfer.banking.server.domain.memberaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;

public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {

}
