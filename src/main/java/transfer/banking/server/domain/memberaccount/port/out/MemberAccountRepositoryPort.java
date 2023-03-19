package transfer.banking.server.domain.memberaccount.port.out;

import java.util.List;
import java.util.Optional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 멤버 계좌 Repository Port Interface
 */
public interface MemberAccountRepositoryPort {

  void save(MemberDomain memberDomain, AccountDomain accountDomain);

  Optional<MemberAccountDomain> findFriendAccountByNumAndBank(String friendAccountNumber, Bank friendAccountBank);

  List<MemberAccountDomain> searchFriendsAccount(List<String> myFriendsAccountNumbers);
}
