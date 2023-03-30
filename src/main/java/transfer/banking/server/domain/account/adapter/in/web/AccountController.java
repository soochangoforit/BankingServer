package transfer.banking.server.domain.account.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountBalanceSearchDto;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDto;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountSearchDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountTransferDto;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountTransferDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountBalanceSearchResDto;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDto;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountSearchDtoResCommand;
import transfer.banking.server.domain.account.application.port.in.AccountBalanceSearchUseCase;
import transfer.banking.server.domain.account.application.port.in.AccountOpenUseCase;
import transfer.banking.server.domain.account.application.port.in.AccountTransferUseCase;
import transfer.banking.server.global.response.DataResponse;
import transfer.banking.server.global.response.MessageResponse;

/**
 * 계좌 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final AccountOpenUseCase accountOpenUseCase;
  private final AccountBalanceSearchUseCase accountBalanceSearchUseCase;
  private final AccountTransferUseCase accountTransferUseCase;

  /**
   * 계좌 개설
   *
   * @param accountOpenDto 개설하고자 하는 계좌 정보
   * @return 계좌 개설 결과
   */
  @PostMapping()
  public ResponseEntity<DataResponse<AccountOpenedDto>> openAccount(
      @RequestBody AccountOpenDto accountOpenDto) {

    AccountOpenDtoCommand accountOpenDtoCommand = accountOpenDto.toCommand();

    AccountOpenedDtoCommand accountOpenedDtoCmd = accountOpenUseCase.openAccount(
        accountOpenDtoCommand);

    AccountOpenedDto accountOpenedDto = new AccountOpenedDto(accountOpenedDtoCmd);

    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "계좌 개설 성공", accountOpenedDto),
        HttpStatus.CREATED);
  }

  /**
   * 계좌 잔액 조회
   *
   * @param accountBalanceSearchDto 계좌 잔액 조회 요청 DTO
   * @return 계좌 잔액 조회 결과 DTO
   */
  @GetMapping()
  public ResponseEntity<DataResponse<AccountBalanceSearchResDto>> searchAccountBalance(
      @RequestBody AccountBalanceSearchDto accountBalanceSearchDto) {
    AccountSearchDtoCommand command = accountBalanceSearchDto.toCommand();
    AccountSearchDtoResCommand resCommand = accountBalanceSearchUseCase.searchAccountBalance(
        command);
    AccountBalanceSearchResDto resDto = new AccountBalanceSearchResDto(resCommand);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "계좌 잔액 조회 성공", resDto),
        HttpStatus.OK);
  }

  /**
   * 계좌 이체
   * - api 요청자 member id
   * - 이체할 계좌 정보
   * - 이체 받을 계좌 정보
   * - 이체 금액
   */
  @PostMapping("/transfer")
  public ResponseEntity<MessageResponse> transfer(@RequestBody AccountTransferDto accountTransferDto) {
    AccountTransferDtoCommand command = accountTransferDto.toCommand();
    accountTransferUseCase.transfer(command);
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "계좌 이체 성공"), HttpStatus.OK);
  }

}

