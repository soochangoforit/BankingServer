package transfer.banking.server.domain.account.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDto;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDto;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDtoCommand;
import transfer.banking.server.domain.account.application.port.in.AccountOpenUseCase;
import transfer.banking.server.global.response.DataResponse;

/**
 * 계좌 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final AccountOpenUseCase accountOpenUseCase;

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

    AccountOpenedDtoCommand accountOpenedDtoCmd = accountOpenUseCase.openAccount(accountOpenDtoCommand);

    AccountOpenedDto accountOpenedDto = new AccountOpenedDto(accountOpenedDtoCmd);

    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "계좌 개설 성공", accountOpenedDto),
        HttpStatus.CREATED);
  }

}

