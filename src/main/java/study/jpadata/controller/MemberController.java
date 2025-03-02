package study.jpadata.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.jpadata.entity.Member;
import study.jpadata.repository.MemberRepository;
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
}
