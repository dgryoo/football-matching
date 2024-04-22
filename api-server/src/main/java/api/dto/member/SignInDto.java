package api.dto.member;

import com.pfairplay.mysql.core.entity.Gender;
import com.pfairplay.mysql.core.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @NotBlank(message = "이름을 작성해 주세요.")
    private String name;
    @NotBlank(message = "닉네임을 작성해 주세요.")
    private String nickname;
    @NotBlank(message = "생년월일을 작성해 주세요.")
    private String birthDate;
    @NotBlank(message = "핸드폰 번호를 작성해 주세요.")
    private String phoneNumber;
    private Gender gender;
    private List<String> preferredPositions;


    public Member toEntity() {
        Member member = new Member();
        member.setName(name);
        member.setNickname(nickname);
        member.setBirthDate(LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE));
        member.setPhoneNumber(phoneNumber);
        if (gender != null) member.setGender(gender);
        if (preferredPositions != null) member.setPreferredPositions(String.join(",", preferredPositions));

        return member;
    }
}
