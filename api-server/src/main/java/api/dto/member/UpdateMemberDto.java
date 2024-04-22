package api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberDto {

    private String nickname;

    private String phoneNumber;

    private List<String> preferredPositions;

}
