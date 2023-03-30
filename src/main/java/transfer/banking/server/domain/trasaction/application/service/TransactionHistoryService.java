package transfer.banking.server.domain.trasaction.application.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.application.service.AccountService;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.request.TransactionHistoryDtoCommand;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.response.TransactionHistoryResponseDtoCommand;
import transfer.banking.server.domain.trasaction.application.port.in.TransactionHistoryUseCase;
import transfer.banking.server.domain.trasaction.domain.TransactionDomain;

/**
 * 계좌 이체 내역 조회 복합 서비스
 */
@Service
@RequiredArgsConstructor
public class TransactionHistoryService implements TransactionHistoryUseCase {

  private final TransactionService transactionService;
  private final AccountService accountService;
  private final MemberAccountService memberAccountService;

  /**
   * 계좌 이체 내역 조회
   *
   * @param command 이체 내역을 확인하고자 하는 계좌 정보
   * @return 이체 내역 목록
   */
  @Override
  public List<TransactionHistoryResponseDtoCommand> getTransactionHistory(TransactionHistoryDtoCommand command) {

    // 이체 내역을 확인하고자 하는 계좌 조회
    AccountDomain accountDomain = accountService.findAccountByBankAndNumber(
        command.getBank(), command.getAccountNumber());

    // memberId 와 계좌 주인이 맞는지 확인
    memberAccountService.checkIfMemberOwnsAccount(command.getMemberId(), accountDomain.getId());

    // accountNumber를 통해서 계좌 내역 조회
    List<TransactionDomain> allTransactionHistory = transactionService.getAllTransactionHistory(
        accountDomain);

    return allTransactionHistory.stream()
        .map(transactionDomain -> new TransactionHistoryResponseDtoCommand(transactionDomain, accountDomain))
        .collect(Collectors.toList());
  }

}
