package transfer.banking.server.domain.trasaction.adapter.out.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.account.application.mapper.AccountMapper;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.trasaction.application.mapper.TransactionMapper;
import transfer.banking.server.domain.trasaction.application.port.out.TransactionRepositoryPort;
import transfer.banking.server.domain.trasaction.adapter.out.persistence.entity.Transaction;
import transfer.banking.server.domain.trasaction.domain.TransactionDomain;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransactionJpaAdapter implements TransactionRepositoryPort {

  private final TransactionMapper transactionMapper;
  private final TransactionRepository transactionRepository;
  private final AccountMapper accountMapper;

  @Override
  public void save(AccountDomain myAccount, AccountDomain friendAccount, BigDecimal transferAmount,
      BigDecimal senderLeftBalance, BigDecimal receiverLeftBalance) {

    Transaction transaction = transactionMapper.toJpaEntity(myAccount, friendAccount, transferAmount,
        senderLeftBalance, receiverLeftBalance);

    transactionRepository.save(transaction);
  }

  @Override
  public List<TransactionDomain> findAllByAccount(AccountDomain accountDomain) {
    Account myAccount = accountMapper.toJpaEntity(accountDomain);
    log.info("계좌 이체 내역을 조회합니다. 계좌번호: {}", myAccount.getAccountNumber());
    return transactionRepository.findAllBySenderOrReceiver(myAccount)
        .stream()
        .map(transactionMapper::toDomain).collect(
            Collectors.toList());
  }
}
