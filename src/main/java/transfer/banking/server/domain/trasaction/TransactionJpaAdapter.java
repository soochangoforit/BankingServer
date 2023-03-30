package transfer.banking.server.domain.trasaction;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.trasaction.entity.Transaction;

@Repository
@RequiredArgsConstructor
public class TransactionJpaAdapter implements TransactionRepositoryPort {

  private final TransactionMapper transactionMapper;
  private final TransactionRepository transactionRepository;

  @Override
  public void save(AccountDomain myAccount, AccountDomain friendAccount, BigDecimal transferAmount,
      BigDecimal senderLeftBalance, BigDecimal receiverLeftBalance) {

    Transaction transaction = transactionMapper.toJpaEntity(myAccount, friendAccount, transferAmount,
        senderLeftBalance, receiverLeftBalance);

    transactionRepository.save(transaction);
  }
}
