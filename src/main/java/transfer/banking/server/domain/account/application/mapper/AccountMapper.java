package transfer.banking.server.domain.account.application.mapper;


import org.springframework.stereotype.Component;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.account.domain.AccountDomain;

@Component
public class AccountMapper {


  public Account toEntity(AccountDomain accountDomain) {
    return Account.builder()
        .id(accountDomain.getId())
        .accountNumber(accountDomain.getAccountNumber())
        .accountName(accountDomain.getAccountName())
        .bank(accountDomain.getBank())
        .balance(accountDomain.getBalance())
        .build();
  }

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
