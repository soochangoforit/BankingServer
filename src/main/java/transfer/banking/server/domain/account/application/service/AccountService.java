package transfer.banking.server.domain.account.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.out.persistence.AccountRepositoryPort;
import transfer.banking.server.domain.account.domain.AccountDomain;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

  private final AccountRepositoryPort accountRepository;

  public boolean checkIfAccountNumberExists(String accountNumber) {
    return accountRepository.existsByAccountNumber(accountNumber);
  }

  public AccountDomain openAccount(AccountDomain accountDomain) {
    return accountRepository.save(accountDomain);
  }
}
