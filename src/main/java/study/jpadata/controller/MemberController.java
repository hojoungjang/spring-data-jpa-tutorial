package study.jpadata.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.jpadata.dto.MemberDto;
import study.jpadata.entity.Member;
import study.jpadata.repository.MemberRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberRepository memberRepository;

    @GetMapping("/api/v1/members/{id}")
    public String getMemberName(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getName();
    }

    @GetMapping("/api/v2/members/{id}")
    public String getMethodName(@PathVariable("id") Member member) {
        return member.getName();
    }

    @GetMapping("/api/v1/members")
    public Page<Member> list(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members;
    }

    @GetMapping("/api/v2/members")
    public Page<MemberDto> listV2(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        Page<MemberDto> membersDto = members.map(MemberDto::new);
        return membersDto;
    }
}
