package transfer.banking.server.domain.account.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.application.port.out.AccountRepositoryPort;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.account.application.mapper.AccountMapper;
import transfer.banking.server.domain.account.domain.AccountDomain;

/**
 * 계좌 Repository Jpa Adapter Class
 */
@Repository
@RequiredArgsConstructor
public class AccountRepositoryJpaAdapter implements AccountRepositoryPort {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  @Override
  @Transactional(readOnly = true)
  public boolean existsByAccountNumber(String accountNumber) {
    return accountRepository.existsByAccountNumber(accountNumber);
  }

  @Override
  @Transactional
  public AccountDomain save(AccountDomain accountDomain) {
    Account account = accountMapper.toJpaEntity(accountDomain);
    accountRepository.save(account);
    return accountMapper.toDomain(account);
  }
}
