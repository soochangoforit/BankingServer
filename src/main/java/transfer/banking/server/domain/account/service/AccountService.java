package transfer.banking.server.domain.account.service;

import static transfer.banking.server.global.exception.ErrorCode.ACCOUNT_NOT_FOUND;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.account.exception.NotFoundAccountException;
import transfer.banking.server.domain.account.repository.AccountRepository;

/**
 * 계좌 순수 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

  private final AccountRepository accountRepository;

  /**
   * 계좌 개설
   *
   * @param account 개설된 계좌
   * @return 저장된 계좌
   */
  @Transactional
  public Account openAccount(Account account) {
    log.info("계좌를 저장합니다. accountNumber: {}", account.getAccountNumber());
    return accountRepository.save(account);
  }

  /**
   * 계좌 번호를 통해서 계좌를 찾는다.
   *
   * @param accountNumber 계좌 번호
   * @return 계좌
   */
  @Transactional(readOnly = true)
  public Account findAccountByAccountNumber(String accountNumber) {
    log.info("계좌 번호를 통해서 계좌를 조회합니다. accountNumber: {}", accountNumber);
    return accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new NotFoundAccountException(ACCOUNT_NOT_FOUND));
  }

  /**
   * 계좌 번호가 존재하는지 확인한다.
   *
   * @param accountNumber 계좌 번호
   * @return 계좌 번호가 존재하면 true, 아니면 false
   */
  @Transactional(readOnly = true)
  public boolean checkIfAccountNumberExists(String accountNumber) {
    log.info("계좌 번호가 존재하는지 확인합니다. accountNumber: {}", accountNumber);
    return accountRepository.findByAccountNumber(accountNumber).isPresent();
  }
}
