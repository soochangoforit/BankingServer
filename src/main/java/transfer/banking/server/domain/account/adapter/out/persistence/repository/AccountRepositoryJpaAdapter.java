package transfer.banking.server.domain.account.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.AccountRepositoryPort;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.account.application.mapper.AccountMapper;
import transfer.banking.server.domain.account.domain.AccountDomain;

@Service
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
  public AccountDomain save(AccountDomain accountDomain) {
    Account account = accountMapper.toEntity(accountDomain);
    Account saved = accountRepository.save(account);
    return accountMapper.toDomain(saved);
  }
}
