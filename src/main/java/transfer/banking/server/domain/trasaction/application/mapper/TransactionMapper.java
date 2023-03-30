package transfer.banking.server.domain.trasaction.application.mapper;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import transfer.banking.server.domain.account.application.mapper.AccountMapper;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.trasaction.adapter.out.persistence.entity.Transaction;
import transfer.banking.server.domain.trasaction.domain.TransactionDomain;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

  private final AccountMapper accountMapper;

  /**
   * 도메인 객체를 JPA 엔티티로 변환
   *
   * @param myAccount 내 계좌 도메인 객체
   * @param friendAccount 친구 계좌 도메인 객체
   * @param transferAmount 이체 금액
   * @param senderLeftBalance 계좌 이체 후 송신자 계좌의 잔액
   * @param receiverLeftBalance 계좌 이체 후 수신자 계좌의 잔액
   * @return JPA 엔티티 객체
   */
  public Transaction toJpaEntity(AccountDomain myAccount, AccountDomain friendAccount,
      BigDecimal transferAmount, BigDecimal senderLeftBalance, BigDecimal receiverLeftBalance) {

    return Transaction.builder()
        .sender(accountMapper.toJpaEntity(myAccount))
        .receiver(accountMapper.toJpaEntity(friendAccount))
        .amount(transferAmount)
        .senderLeftBalance(senderLeftBalance)
        .receiverLeftBalance(receiverLeftBalance)
        .build();
  }

  public TransactionDomain toDomain(Transaction transaction) {
    return TransactionDomain.builder()
        .sender(accountMapper.toDomain(transaction.getSender()))
        .receiver(accountMapper.toDomain(transaction.getReceiver()))
        .amount(transaction.getAmount())
        .senderLeftBalance(transaction.getSenderLeftBalance())
        .receiverLeftBalance(transaction.getReceiverLeftBalance())
        .transactionDate(transaction.getCreatedAt())
        .build();
  }
}
