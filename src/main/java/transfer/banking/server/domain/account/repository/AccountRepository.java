package transfer.banking.server.domain.account.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import transfer.banking.server.domain.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByAccountNumber(String accountNumber);
}
