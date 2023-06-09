# BankingServer

### 기능 요구사항

---

### 회원가입/로그인

1. 사용자는 아이디, 비밀번호, 이름을 입력하여 회원가입을 진행한다.
2. 사용자는 아이디와 비밀번호를 입력하여 로그인을 한다.

### 친구 추가 / 친구 목록 조회

1. 사용자는 자신을 제외한 사용자를 친구로 추가할 수 있다.
   - 친구 추가 시, 친구 이름과 전화번호를 통해서 친구 추가를 할 수 있다.
   - 친구 요청을 받은 사용자는 친구 요청한 사용자와 자동으로 친구가 된다.

2. 사용자는 친구 목록을 확인할 수 있다.
   - 친구 목록 조회 시, 이름 전화번호를 확인할 수 있다.

### 계좌 생성

1. 계좌를 만들 수 있다.
   - 계좌 생성 시 은행, 계좌 이름 넣으면 계좌를 개설할 수 있다.
   - 계좌번호는 내부적으로 랜덤 숫자를 통해서 만들어진다.

2. 한 사람은 여러 개의 계좌를 만들 수 있다.
   - 공동 명의 계좌에 대해선, 아직 고려하진 않겠다.


### 계좌 이체

1. 계좌 이체는 친구끼리만 가능하다.
   - 계좌 이체 시, 송금하고자 하는 계좌 번호와 은행의 주인이 친구 관계여야 한다.

2. 하나의 계좌에 동시에 돈이 입금되는 경우, 개별적인 처리가 되도록 해결해야 한다.
   - 동시성 이슈를 제어할 필요가 있다.
   - DataBase 는 1개로 가정한다.


### 계좌 조회

1. 회원은 자신의 계좌를 모두 조회할 수 있다.
   - 전체 조회 시, 해당 계좌의 남은 금액 모두를 확인할 수 있다.

### 이체 내역 조회

1. 계좌 이체한 내역을 확인할 수 있다.
   - 자신의 계좌에 들어가면, 지금까지 입출금 내역을 모두 확인할 수 있다.
   - 아래의 사진처럼 + , - 를 통해 입금인지 출금인지 확인할 수 있다.
   - 또한, 출금 입금에 따라 해당 시점에 잔액이 얼마 있었는지 추적할 수 있다.

