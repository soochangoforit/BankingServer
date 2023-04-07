package transfer.banking.server.domain.account.application.service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.trasaction.application.service.TransactionService;

/**
 * 계좌 이체 트랜잭션 서비스
 */
@Service
@RequiredArgsConstructor
public class TransferService {

  private final AccountService accountService;
  private final TransactionService transactionService;

  /**
   * 계좌 이체
   *
   * [데드락 발생 가능한 경우]
   * 쓰레드1 : A 계좌 -> B 계좌로 이체
   * 쓰레드2 : B 계좌 -> A 계좌로 이체
   * 비지니스 로직에서, 2개의 쓰레드가 각각 자신의 계좌 먼저 조회를 함으로써 Lock 을 걸게 되면
   * 두 쓰레드 모두 친구 계좌를 조회하고 싶어도 데드락이 발생한다.
   *
   * [데드락 해결 방법]
   * 쓰레드1 : A 계좌 -> B 계좌로 이체
   * 쓰레드2 : B 계좌 -> A 계좌로 이체
   * 비지니스 로직 안에서, 계좌 번호가 더 작은 계좌를 먼저 조회하도록 하여 Lock 을 걸게 되는 작은 규칙을 만듦으로써
   * 데드락을 방지할 수 있다.
   * 즉, Lock 을 거는 순서를 쓰레드 마다 일관되게 유지하면 된다.
   *
   * 예를 들어, A 계좌가 B 계좌보다 계좌 번호가 작다면,
   * 서로 다른 쓰레드 내에서 A 계좌를 먼저 조회하고, 그 다음 B 계좌를 조회하도록 한다.
   * 그렇게 Lock 을 얻게 되는 순서를 일관되게 유지하면 데드락을 방지할 수 있다.
   */
  @Transactional
  public void transfer(Bank myBank, String myAccountNumber, MemberAccountDomain friendAccountDomain,
      BigDecimal transferAmount) {

    AccountDomain fromAccountDomain;
    AccountDomain toAccountDomain;
    String friendAccountNumber = friendAccountDomain.getAccount().getAccountNumber();
    Bank friendAccountBank = friendAccountDomain.getAccount().getBank();

    if (isSmallerThanFriendNumber(myAccountNumber, friendAccountNumber)) {
      fromAccountDomain = lockAndGetAccount(myBank, myAccountNumber);
      toAccountDomain = lockAndGetAccount(friendAccountBank, friendAccountNumber);
    } else {
      toAccountDomain = lockAndGetAccount(friendAccountBank, friendAccountNumber);
      fromAccountDomain = lockAndGetAccount(myBank, myAccountNumber);
    }

    performTransfer(fromAccountDomain, toAccountDomain, transferAmount);
  }

  private boolean isSmallerThanFriendNumber(String myAccountNumber, String friendAccountNumber) {
    return Integer.parseInt(myAccountNumber) < Integer.parseInt(friendAccountNumber);
  }

  private AccountDomain lockAndGetAccount(Bank bank, String accountNumber) {
    return accountService.findAccountByBankAndNumberWithLock(bank, accountNumber);
  }

  private void performTransfer(AccountDomain fromAccountDomain, AccountDomain toAccountDomain,
      BigDecimal transferAmount) {

    fromAccountDomain.withdraw(transferAmount);
    toAccountDomain.deposit(transferAmount);

    accountService.flushAndSave(fromAccountDomain);
    accountService.flushAndSave(toAccountDomain);

    transactionService.saveTransactionRecord(fromAccountDomain, toAccountDomain, transferAmount);
  }
}
