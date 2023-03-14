package transfer.banking.server.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberService memberService;

  @Test
  @DisplayName("회원가입 성공")
  void testSignUp_Success() {
    // Arrange
    Member member = new Member("username", "password", "email", "이수찬", "01012345678");
    when(memberRepository.save(any(Member.class))).thenReturn(member);

    //Act
    Member result = memberService.signUp(member);

    //Assert
    assertEquals(member.getUsername(), result.getUsername());
    assertEquals(member.getPassword(), result.getPassword());
    assertEquals(member.getEmail(), result.getEmail());
    assertEquals(member.getName(), result.getName());
    assertEquals(member.getPhoneNumber(), result.getPhoneNumber());
  }




}