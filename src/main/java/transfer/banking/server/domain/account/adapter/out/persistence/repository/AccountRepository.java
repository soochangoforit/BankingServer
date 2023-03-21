package transfer.banking.server.domain.account.adapter.out.persistence.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;

public interface AccountRepository extends JpaRepository<Account, Long> {


  boolean existsByAccountNumber(String accountNumber);

  @Query("select a from Account a where a.bank = :accountBank and a.accountNumber = :accountNumber")
  Optional<Account> findAccountIdByBankAndNumber(@Param("accountBank") Bank accountBank, @Param("accountNumber") String accountNumber);

  @Lock(value = LockModeType.PESSIMISTIC_WRITE)
  @Query("select a from Account a where a.bank = :bank and a.accountNumber = :accountNumber")
  Optional<Account> findAccountIdByBankAndNumberWithLock(@Param("bank") Bank bank, @Param("accountNumber") String accountNumber);
}
