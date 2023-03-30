package transfer.banking.server.domain.trasaction.application.port.out;

import java.math.BigDecimal;
import java.util.List;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.trasaction.domain.TransactionDomain;

public interface TransactionRepositoryPort {

  void save(AccountDomain myAccount, AccountDomain friendAccount, BigDecimal transferAmount, BigDecimal senderLeftBalance, BigDecimal receiverLeftBalance);

  List<TransactionDomain> findAllByAccount(AccountDomain accountDomain);
}
