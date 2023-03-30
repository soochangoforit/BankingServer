package transfer.banking.server.domain.trasaction.application.port.in;

import java.util.List;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.request.TransactionHistoryDtoCommand;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.response.TransactionHistoryResponseDtoCommand;

/**
 * 계좌 이체 내역 조회 UseCase
 */
public interface TransactionHistoryUseCase {

  List<TransactionHistoryResponseDtoCommand> getTransactionHistory(TransactionHistoryDtoCommand command);
}
