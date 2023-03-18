package transfer.banking.server.domain.memberaccount.port.out;

import java.util.Optional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;

public interface MemberAccountRepositoryPort {

  void save(MemberDomain memberDomain, AccountDomain accountDomain);

  Optional<MemberAccountDomain> findFriendAccountByNumAndBank(String friendAccountNumber, Bank friendAccountBank);
}
