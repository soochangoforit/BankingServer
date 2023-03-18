package transfer.banking.server.domain.account.application.port.out;

import java.util.Optional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;

public interface AccountRepositoryPort {

  boolean existsByAccountNumber(String accountNumber);

  AccountDomain save(AccountDomain accountDomain);
}
