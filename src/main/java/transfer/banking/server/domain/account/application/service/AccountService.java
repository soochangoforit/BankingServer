package transfer.banking.server.domain.account.application.service;

import static transfer.banking.server.global.exception.ErrorCode.FRIEND_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.port.out.AccountRepositoryPort;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.global.exception.ErrorCode;

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
