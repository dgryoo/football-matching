package api.member;

import api.dto.member.LoginDto;
import api.dto.member.MemberDto;
import api.dto.member.SignInDto;
import api.dto.member.UpdateMemberDto;
import api.util.JsonConverter;
import api.util.TestObjectGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MemberControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public MemberControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void succeedToSignIn() throws Exception {
        // given
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();


        // when
        MockHttpServletResponse signInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(signInDto)))
                .andReturn()
                .getResponse();

        // then
        MemberDto memberDto = JsonConverter.parseJsonString(signInResponse.getContentAsString(), MemberDto.class);
        assertEquals(HttpStatus.OK.value(), signInResponse.getStatus());
        assertEquals(signInDto.getPhoneNumber(), memberDto.getPhoneNumber());
    }

    @Test
    public void failedToSignInByDuplicatedPhoneNumber() throws Exception {
        // given
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();
        SignInDto duplicatedPhoneNumberSignInDto = TestObjectGenerator.generateTestSignInDto();

        // when
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/members/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJsonString(signInDto)));

        MockHttpServletResponse expectedFailSignInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(duplicatedPhoneNumberSignInDto)))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.CONFLICT.value(), expectedFailSignInResponse.getStatus());

    }

    @Test
    public void succeedToSignOut() throws Exception {
        // given
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();

        // when
        MockHttpServletResponse signInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(signInDto)))
                .andReturn()
                .getResponse();
        MemberDto savedMemberDto = JsonConverter.parseJsonString(signInResponse.getContentAsString(), MemberDto.class);

        MockHttpServletResponse signOutResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(String.format("/api/members/%s/sign-out", savedMemberDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), signOutResponse.getStatus());

    }

    @Test
    public void failedToSignOutBySourceNotFound() throws Exception {
        // given
        String nonExistedId = "nonExistedId";

        // when
        MockHttpServletResponse signOutResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(String.format("/api/members/%s/sign-out", nonExistedId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), signOutResponse.getStatus());

    }

    @Test
    public void succeedToLogIn() throws Exception {
        // given
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();
        LoginDto loginDto = new LoginDto(signInDto.getPhoneNumber());

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(signInDto)))
                .andReturn()
                .getResponse();

        MockHttpServletResponse logInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(loginDto)))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), logInResponse.getStatus());

    }

    @Test
    public void failedToLogInBySourceNotFound() throws Exception {
        // given
        LoginDto loginDto = new LoginDto("nonExistedPhoneNumber");

        // when
        MockHttpServletResponse logInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(loginDto)))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), logInResponse.getStatus());

    }

    @Test
    public void succeedToLogout() throws Exception {
        // given
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();

        LoginDto loginDto = new LoginDto(signInDto.getPhoneNumber());

        // when
        MockHttpServletResponse signInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(signInDto)))
                .andReturn()
                .getResponse();

        MemberDto savedMemberDto = JsonConverter.parseJsonString(signInResponse.getContentAsString(), MemberDto.class);

        MockHttpServletResponse logOutResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(String.format("/api/members/%s/logout", savedMemberDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(loginDto)))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), logOutResponse.getStatus());

    }

    @Test
    public void failedToLogoutBySourceNotFound() throws Exception {
        // given
        String nonExistedId = "nonExistedId";

        // when
        MockHttpServletResponse expectedFailLogOutResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(String.format("/api/members/%s/logout", nonExistedId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), expectedFailLogOutResponse.getStatus());

    }

    @Test
    public void succeedToGetMember() throws Exception {
        // given
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();

        // when
        MockHttpServletResponse signInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(signInDto)))
                .andReturn()
                .getResponse();

        MemberDto savedMember = JsonConverter.parseJsonString(signInResponse.getContentAsString(), MemberDto.class);

        MockHttpServletResponse getMemberResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/api/members/%s", savedMember.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        MemberDto memberDto = JsonConverter.parseJsonString(getMemberResponse.getContentAsString(), MemberDto.class);

        // then
        assertEquals(HttpStatus.OK.value(), getMemberResponse.getStatus());
        assertEquals(savedMember.getId(), memberDto.getId());

    }

    @Test
    public void failedToGetMember() throws Exception {
        // given
        String nonExistedId = "nonExistedId";

        // when
        MockHttpServletResponse getMemberResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/api/members/%s", nonExistedId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), getMemberResponse.getStatus());

    }

    @Test
    public void succeedToUpdateMember() throws Exception {
        // given
        SignInDto signInDto = TestObjectGenerator.generateTestSignInDto();

        UpdateMemberDto updateMemberDto = new UpdateMemberDto(
                "updatedNickname",
                "010-1111-2222",
                Arrays.asList("ST", "RW", "LW")
        );

        // when
        MockHttpServletResponse signInResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(signInDto)))
                .andReturn()
                .getResponse();
        MemberDto savedMemberDto = JsonConverter.parseJsonString(signInResponse.getContentAsString(), MemberDto.class);

        MockHttpServletResponse updateMemberResponse = mockMvc.perform(MockMvcRequestBuilders
                        .put(String.format("/api/members/%s", savedMemberDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(updateMemberDto)))
                .andReturn()
                .getResponse();
        MemberDto updatedMemberDto = JsonConverter.parseJsonString(updateMemberResponse.getContentAsString(), MemberDto.class);

        // then
        assertEquals(HttpStatus.OK.value(), updateMemberResponse.getStatus());
        assertEquals(updateMemberDto.getNickname(), updatedMemberDto.getNickname());
        assertEquals(updateMemberDto.getPhoneNumber(), updatedMemberDto.getPhoneNumber());
        assertEquals(updateMemberDto.getPreferredPositions(), updatedMemberDto.getPreferredPositions());

    }

    @Test
    public void failedToUpdateMember() throws Exception {
        // given
        String nonExistedId = "nonExistedId";

        UpdateMemberDto updateMemberDto = new UpdateMemberDto(
                "updatedNickname",
                "010-1111-2222",
                null
        );

        // when
        MockHttpServletResponse updateMemberResponse = mockMvc.perform(MockMvcRequestBuilders
                        .put(String.format("/api/members/%s", nonExistedId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(updateMemberDto)))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), updateMemberResponse.getStatus());

    }

}
