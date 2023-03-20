package transfer.banking.server.domain.account.application.service;


import static transfer.banking.server.global.exception.ErrorCode.ACCOUNT_NOT_FOUND;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.exception.NotFoundAccountException;
import transfer.banking.server.domain.account.application.port.out.AccountRepositoryPort;
import transfer.banking.server.domain.account.domain.AccountDomain;

/**
 * 계좌 순수 서비스, 필드 값으로 Repository 접근을 위한 Port Interface 를 사용한다.
 * 트랜잭션을 아직 시작하지 않는다.
 * DB 접근을 위한 Repository Adapter Class 에서 트랜잭션을 시작한다.
 * 입력 값으로 Domain 객체를 주입 받거나, Primitive Type 을 사용한다.
 * 응답 값으로 Domain 객체를 사용 하거나, Primitive Type 을 사용한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

  private final AccountRepositoryPort accountRepository;

  /**
   * 계좌 번호 중복 검사
   *
   * @param accountNumber 계좌 번호
   * @return 중복된 계좌 번호가 있으면 true, 없으면 false
   */
  public boolean checkIfAccountNumberExists(String accountNumber) {
    return accountRepository.existsByAccountNumber(accountNumber);
  }

  /**
   * 계좌 개설
   *
   * @param accountDomain 계좌 도메인
   * @return 개설된 계좌 도메인
   */
  public AccountDomain openAccount(AccountDomain accountDomain) {
    return accountRepository.save(accountDomain);
  }

  /**
   * 계좌 조회
   *
   * @param accountBank 계좌 은행
   * @param accountNumber 계좌 번호
   * @return 계좌 도메인
   */
  public AccountDomain findAccountIdByBankAndNumber(Bank accountBank, String accountNumber) {
    return accountRepository.findAccountByBankAndNumber(accountBank, accountNumber)
        .orElseThrow(() -> new NotFoundAccountException(ACCOUNT_NOT_FOUND));
  }
}
