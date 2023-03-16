package transfer.banking.server.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.banking.server.domain.account.dto.request.AccountOpenDto;
import transfer.banking.server.domain.account.dto.response.AccountOpenedDto;
import transfer.banking.server.domain.account.service.AccountOpenService;
import transfer.banking.server.global.response.DataResponse;

/**
 * 계좌 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final AccountOpenService accountOpenService;

  /**
   * 계좌 개설
   *
   * @param accountOpenDto 계좌 개설 요청 DTO
   * @return 계좌 개설 응답 DTO
   */
  @PostMapping()
  public ResponseEntity<DataResponse<AccountOpenedDto>> openAccount(
      @RequestBody AccountOpenDto accountOpenDto) {
    AccountOpenedDto accountOpenedDto = accountOpenService.openAccount(accountOpenDto);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "계좌 개설 성공", accountOpenedDto),
        HttpStatus.CREATED);
  }

}
