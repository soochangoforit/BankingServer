package transfer.banking.server.domain.account.application.port.out;

import transfer.banking.server.domain.account.domain.AccountDomain;

/**
 * 계좌 Repository Port Interface
 */
public interface AccountRepositoryPort {

  boolean existsByAccountNumber(String accountNumber);

  AccountDomain save(AccountDomain accountDomain);
}
