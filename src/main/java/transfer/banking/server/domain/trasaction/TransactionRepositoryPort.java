package transfer.banking.server.domain.trasaction;

import java.math.BigDecimal;
import transfer.banking.server.domain.account.domain.AccountDomain;

public interface TransactionRepositoryPort {

  void save(AccountDomain myAccount, AccountDomain friendAccount, BigDecimal transferAmount, BigDecimal senderLeftBalance, BigDecimal receiverLeftBalance);
}
