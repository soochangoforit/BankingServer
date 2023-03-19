package transfer.banking.server.domain.account.application.mapper;


import org.springframework.stereotype.Component;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.account.domain.AccountDomain;

/**
 * Domain 객체를 Entity 객체로 변환하는 Mapper
 * Entity 객체를 Domain 객체로 변환하는 Mapper
 */
@Component
public class AccountMapper {


  /**
   * Domain 객체를 Entity 객체로 변환
   *
   * @param accountDomain 계좌 도메인 객체
   * @return 계좌 엔티티 객체
   */
  public Account toJpaEntity(AccountDomain accountDomain) {
    return Account.builder()
        .id(accountDomain.getId())
        .accountNumber(accountDomain.getAccountNumber())
        .accountName(accountDomain.getAccountName())
        .bank(accountDomain.getBank())
        .balance(accountDomain.getBalance())
        .build();
  }

  /**
   * Entity 객체를 Domain 객체로 변환
   *
   * @param accountEntity 계좌 엔티티 객체
   * @return 계좌 도메인 객체
   */
  public AccountDomain toDomain(Account accountEntity) {
    return AccountDomain.builder()
        .id(accountEntity.getId())
        .accountName(accountEntity.getAccountName())
        .accountNumber(accountEntity.getAccountNumber())
        .bank(accountEntity.getBank())
        .balance(accountEntity.getBalance())
        .build();
  }
}
