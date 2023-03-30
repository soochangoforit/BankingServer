package transfer.banking.server.domain.trasaction.adapter.in.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.request.TransactionHistoryDto;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.request.TransactionHistoryDtoCommand;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.response.TransactionHistoryResponseDto;
import transfer.banking.server.domain.trasaction.adapter.in.web.dto.response.TransactionHistoryResponseDtoCommand;
import transfer.banking.server.domain.trasaction.application.port.in.TransactionHistoryUseCase;
import transfer.banking.server.global.response.DataResponse;

/**
 * 계좌 이체 내역 조회 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

  private final TransactionHistoryUseCase transactionHistoryUseCase;

  /**
   * 계좌 이체 내역 조회
   *
   * @param transactionHistoryDto 이체 내역을 확인하고자 하는 계좌 정보
   * @return 이체 내역
   */
  @GetMapping("/history")
  public ResponseEntity<DataResponse<List<TransactionHistoryResponseDto>>> searchTransactionHistory(@RequestBody TransactionHistoryDto transactionHistoryDto) {
    TransactionHistoryDtoCommand command = transactionHistoryDto.toCommand();

    List<TransactionHistoryResponseDtoCommand> commands = transactionHistoryUseCase.getTransactionHistory(
        command);

    List<TransactionHistoryResponseDto> responseDtos = TransactionHistoryResponseDto.of(commands);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "거래 내역 조회 성공",responseDtos), HttpStatus.OK);
  }

}
