package api.util;

import api.common.util.DateStringConverter;
import api.dto.member.SignInDto;
import com.pfairplay.mysql.core.entity.Gender;
import com.pfairplay.mysql.core.entity.Member;

public class TestObjectGenerator {

    public static Member generateTestMemberEntity() {
        Member member = new Member();
        member.setId("testId");
        member.setName("testName");
        member.setNickname("testNickname");
        member.setPhoneNumber("010-0000-0000");
        member.setBirthDate(DateStringConverter.fromSimpleDateFormatString("2023-01-01"));
        member.setGender(Gender.unknown);
        member.setPreferredPositions("RW");
        return member;
    }

    public static SignInDto generateTestSignInDto() {
        SignInDto signInDto = new SignInDto(
                "testName",
                "testNickName",
                "2024-01-01",
                "010-1234-5678",
                null,
                null
        );
        return signInDto;
    }
}
