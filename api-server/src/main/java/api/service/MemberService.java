package api.service;


import api.common.exception.custom.DuplicateKeyException;
import api.common.exception.custom.SourceNotFoundException;
import api.dto.member.LoginDto;
import api.dto.member.MemberDto;
import api.dto.member.SignInDto;
import api.dto.member.UpdateMemberDto;
import com.pfairplay.mysql.core.entity.Member;
import com.pfairplay.mysql.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberDto signIn(SignInDto signInDto) {
        Optional<Member> duplicatedMember = memberRepository.findByPhoneNumber(signInDto.getPhoneNumber());

        if (duplicatedMember.isPresent()) {
            throw new DuplicateKeyException();
        }

        Member savedMember = memberRepository.save(signInDto.toEntity());
        return MemberDto.fromEntity(savedMember);
    }

    public MemberDto login(LoginDto loginDto) {
        Member member = memberRepository.findByPhoneNumber(loginDto.getPhoneNumber())
                .orElseThrow(SourceNotFoundException::new);

        return MemberDto.fromEntity(member);
    }

    public void signOut(String id) {
        Member member = memberRepository
                .findById(id)
                .orElseThrow(SourceNotFoundException::new);

        memberRepository.softDeleteById(id);
        member.softDelete();
    }

    @Transactional(readOnly = true)
    public MemberDto getMember(String id) {
        Member member = memberRepository
                .findById(id)
                .orElseThrow(SourceNotFoundException::new);

        return MemberDto.fromEntity(member);

    }

    public MemberDto updateMember(String id, UpdateMemberDto updateMemberDto) {
        Member member = memberRepository
                .findById(id)
                .orElseThrow(SourceNotFoundException::new);

        member.updateMemberInformation(
                updateMemberDto.getPhoneNumber(),
                updateMemberDto.getNickname(),
                String.join(",", updateMemberDto.getPreferredPositions())
        );
        return MemberDto.fromEntity(member);
    }

    public void logout(String id) {
        Member member = memberRepository
                .findById(id)
                .orElseThrow(SourceNotFoundException::new);

        member.logout();
    }

}

