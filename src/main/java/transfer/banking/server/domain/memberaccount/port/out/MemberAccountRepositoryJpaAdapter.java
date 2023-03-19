package transfer.banking.server.domain.memberaccount.port.out;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;
import transfer.banking.server.domain.memberaccount.mapper.MemberAccountMapper;
import transfer.banking.server.domain.memberaccount.repository.MemberAccountRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberAccountRepositoryJpaAdapter implements MemberAccountRepositoryPort{

  private final MemberAccountRepository memberAccountRepository;
  private final MemberAccountMapper memberAccountMapper;

  @Override
  @Transactional
  public void save(MemberDomain memberDomain, AccountDomain accountDomain) {
    MemberAccount memberAccount = memberAccountMapper.toEntity(memberDomain, accountDomain);
    memberAccountRepository.save(memberAccount);
  }

  @Override
  public Optional<MemberAccountDomain> findFriendAccountByNumAndBank(String friendAccountNumber,
      Bank friendAccountBank) {
    return memberAccountRepository.findFriendAccountByNumAndBank(friendAccountNumber, friendAccountBank)
        .map(memberAccountMapper::toDomain);
  }

  @Override
  public List<MemberAccountDomain> searchFriendsAccount(List<String> myFriendsAccountNumbers) {
    return memberAccountRepository.searchFriendsAccount(myFriendsAccountNumbers).stream()
        .map(memberAccountMapper::toDomain)
        .toList();
  }
}
