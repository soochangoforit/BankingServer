package transfer.banking.server.domain.account.adapter.out.persistence.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.port.out.AccountRepositoryPort;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.account.application.mapper.AccountMapper;
import transfer.banking.server.domain.account.domain.AccountDomain;

/**
 * 계좌 Repository Jpa Adapter Class
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountRepositoryJpaAdapter implements AccountRepositoryPort {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  @Override
  @Transactional(readOnly = true)
  public boolean existsByAccountNumber(String accountNumber) {
    log.info("계좌번호가 존재하는지 확인합니다. accountNumber: {}", accountNumber);
    return accountRepository.existsByAccountNumber(accountNumber);
  }

  @Override
  @Transactional
  public AccountDomain save(AccountDomain accountDomain) {
    Account account = accountMapper.toJpaEntity(accountDomain);
    log.info("계좌를 저장합니다. accountNumber: {}", account.getAccountNumber());
    accountRepository.save(account);
    return accountMapper.toDomain(account);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AccountDomain> findAccountByBankAndNumber(Bank accountBank, String accountNumber) {
    log.info("계좌번호로 계좌 아이디를 조회합니다. accountBank: {}, accountNumber: {}", accountBank, accountNumber);
    return accountRepository.findAccountIdByBankAndNumber(accountBank, accountNumber)
        .map(accountMapper::toDomain);
  }
}
