package transfer.banking.server.domain.trasaction;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.domain.AccountDomain;

/**
 * 트랜잭션 기록을 저장하는 순수 Service
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepositoryPort transactionRepository;

  @Transactional
  public void saveTransactionRecord(AccountDomain myAccount, AccountDomain friendAccount,
      BigDecimal transferAmount) {

    BigDecimal senderLeftBalance = myAccount.getBalance();
    BigDecimal receiverLeftBalance = friendAccount.getBalance();

    transactionRepository.save(myAccount, friendAccount, transferAmount, senderLeftBalance,
        receiverLeftBalance);
  }
}
