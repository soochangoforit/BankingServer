package transfer.banking.server.domain.account.adapter.out.persistence;

import transfer.banking.server.domain.account.domain.AccountDomain;

public interface AccountRepositoryPort {

  boolean existsByAccountNumber(String accountNumber);

  AccountDomain save(AccountDomain accountDomain);
}
