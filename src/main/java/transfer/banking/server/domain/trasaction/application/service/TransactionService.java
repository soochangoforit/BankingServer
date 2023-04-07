package transfer.banking.server.domain.trasaction.application.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.trasaction.application.port.out.TransactionRepositoryPort;
import transfer.banking.server.domain.trasaction.domain.TransactionDomain;

/**
 * 트랜잭션 기록을 저장하는 순수 Service
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepositoryPort transactionRepository;

  /**
   * 계좌 이체 내역 저장
   *
   * @param myAccount 이체를 요청한 내 계좌 도메인 객체
   * @param friendAccount 이체를 받는 친구 계좌 도메인 객체
   * @param transferAmount 이체 금액
   */
  @Transactional
  public void saveTransactionRecord(AccountDomain myAccount, AccountDomain friendAccount,
      BigDecimal transferAmount) {

    BigDecimal senderLeftBalance = myAccount.getBalance();
    BigDecimal receiverLeftBalance = friendAccount.getBalance();

    transactionRepository.save(myAccount, friendAccount, transferAmount, senderLeftBalance,
        receiverLeftBalance);
  }

  /**
   * 계좌 이체 내역 조회
   *
   * @param accountDomain 이체 내역을 확인하고자 하는 계좌 도메인 객체
   */
  @Transactional(readOnly = true)
  public List<TransactionDomain> getAllTransactionHistory(AccountDomain accountDomain) {
    return transactionRepository.findAllByAccount(accountDomain);
  }

  public void deleteAll() {
    transactionRepository.deleteAll();
  }
}
