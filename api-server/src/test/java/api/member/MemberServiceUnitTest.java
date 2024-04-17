package api.member;

import api.common.exception.custom.DuplicateKeyException;
import api.dto.member.MemberDto;
import api.dto.member.SignInDto;
import api.service.MemberService;
import api.util.TestObjectGenerator;
import com.pfairplay.mysql.core.entity.Member;
import com.pfairplay.mysql.core.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@MockitoSettings
@Transactional
@ActiveProfiles("test")
public class MemberServiceUnitTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void succeedToSignIn() {
        // given
        Member member = TestObjectGenerator.generateTestMemberEntity();
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        MemberDto memberDto = memberService.signIn(signInDto);

        // then
        Assertions.assertEquals(memberDto.getId(), member.getId());
    }

    @Test
    public void failedToSignInByDuplicatePhoneNumber() {
        // given
        Member member = TestObjectGenerator.generateTestMemberEntity();
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();
        when(memberRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.of(member));

        // when
        signInDto.setPhoneNumber(member.getPhoneNumber());

        // then
        assertThrows(DuplicateKeyException.class, () -> memberService.signIn(signInDto));

    }

}
