package transfer.banking.server.domain.account.application.service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

/**
 * 계좌 이체 트랜잭션 서비스
 */
@Service
@RequiredArgsConstructor
public class TransferService {

  private final AccountService accountService;

  @Transactional
  public void transfer(Bank myBank, String fromAccountNumber, MemberAccountDomain friendAccountDomain,
      BigDecimal transferAmount) {

    // 내 계좌 조회
    AccountDomain myAccount = accountService.findAccountByBankAndNumberWithLock(
        myBank, fromAccountNumber);

    // 내 계좌에서 돈 출금
    myAccount.withdraw(transferAmount);

    // 친구 계좌 조회
    AccountDomain friendAccount = accountService.findAccountByBankAndNumberWithLock(
        friendAccountDomain.getAccount().getBank(),
        friendAccountDomain.getAccount().getAccountNumber());

    // 친구 계좌에 돈 입금
    friendAccount.deposit(transferAmount);

    accountService.flushAndSave(myAccount);
    accountService.flushAndSave(friendAccount);
  }
}
