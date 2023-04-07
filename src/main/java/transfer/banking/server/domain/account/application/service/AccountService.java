package transfer.banking.server.domain.account.application.service;


import static transfer.banking.server.global.exception.ErrorCode.ACCOUNT_NOT_FOUND;


import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.exception.NotFoundAccountException;
import transfer.banking.server.domain.account.application.port.out.AccountRepositoryPort;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

/**
 * 계좌 순수 서비스, 필드 값으로 Repository 접근을 위한 Port Interface 를 사용한다. 트랜잭션을 아직 시작하지 않는다. DB 접근을 위한
 * Repository Adapter Class 에서 트랜잭션을 시작한다. 입력 값으로 Domain 객체를 주입 받거나, Primitive Type 을 사용한다. 응답 값으로
 * Domain 객체를 사용 하거나, Primitive Type 을 사용한다.
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
  @Transactional(readOnly = true)
  public boolean checkIfAccountNumberExists(String accountNumber) {
    return accountRepository.existsByAccountNumber(accountNumber);
  }

  /**
   * 계좌 개설
   *
   * @param accountDomain 계좌 도메인
   * @return 개설된 계좌 도메인
   */
  @Transactional
  public AccountDomain openAccount(AccountDomain accountDomain) {
    return accountRepository.save(accountDomain);
  }

  /**
   * 계좌 조회
   *
   * @param accountBank   계좌 은행
   * @param accountNumber 계좌 번호
   * @return 계좌 도메인
   */
  @Transactional(readOnly = true)
  public AccountDomain findAccountByBankAndNumber(Bank accountBank, String accountNumber) {
    return accountRepository.findAccountByBankAndNumber(accountBank, accountNumber)
        .orElseThrow(() -> new NotFoundAccountException(ACCOUNT_NOT_FOUND));
  }

  /**
   * 실제 계좌 이체를 담당하는 메소드 하나의 트랜잭션으로 처리한다.
   *
   * @param myAccountDomain     내 계좌 도메인
   * @param friendAccountDomain 친구 계좌 도메인
   * @param transferAmount      이체 하고자 하는 금액
   */
  @Transactional
  public void transfer(AccountDomain myAccountDomain, MemberAccountDomain friendAccountDomain,
      BigDecimal transferAmount) {
    // 내 계좌에서 이체 금액을 차감한다.
    myAccountDomain.withdraw(transferAmount);
    // 친구 계좌에 이체 금액을 입금한다.
    friendAccountDomain.deposit(transferAmount);

  }

  @Transactional
  public void flushAndSave(AccountDomain myAccountDomain) {
    log.info("계좌 변경 내역을 저장합니다. {} 주인", myAccountDomain.getAccountName());
    accountRepository.save(myAccountDomain);
  }

  @Transactional(readOnly = true)
  public AccountDomain findAccountByBankAndNumberWithLock(Bank bank, String accountNumber) {
    return accountRepository.findAccountByBankAndNumberWithLock(bank, accountNumber)
        .orElseThrow(() -> new NotFoundAccountException(ACCOUNT_NOT_FOUND));
  }

  public void deleteAll() {
    accountRepository.deleteAll();
  }
}
