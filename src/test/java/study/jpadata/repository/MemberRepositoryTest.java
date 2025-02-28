package study.jpadata.repository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import study.jpadata.entity.Member;
import study.jpadata.entity.Team;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        Assertions.assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        Assertions.assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void testFindByUsernameAndAgeGreaterThan() {
        List<Member> members = new ArrayList<>(Arrays.asList(
            new Member("member1", 10),
            new Member("member1", 13),
            new Member("member2", 21),
            new Member("member2", 16),
            new Member("member3", 25)
        ));
        members.forEach(m -> memberRepository.save(m));

        List<Member> findMembers1 = memberRepository.findByNameAndAgeGreaterThan("member1", 5);
        assertEquals(findMembers1.size(), 2);
        findMembers1.forEach(m -> {
            assertEquals(m.getName(), "member1");
            assertTrue(m.getAge() > 5);
        });
    }

    @Test
    public void testFindByUsername() {
        List<Member> members = new ArrayList<>(Arrays.asList(
            new Member("member1", 10),
            new Member("member2", 13),
            new Member("member3", 21)
        ));
        members.forEach(m -> memberRepository.save(m));

        List<Member> findMembers = memberRepository.findByUsername("member2");

        assertEquals(1, findMembers.size());
        assertEquals("member2", findMembers.get(0).getName());
        assertEquals(13, findMembers.get(0).getAge());
    }

    @Test
    public void testCustomQuery() {
        List<Member> members = new ArrayList<>(Arrays.asList(
            new Member("member1", 10),
            new Member("member1", 13),
            new Member("member2", 21),
            new Member("member2", 16),
            new Member("member3", 25)
        ));
        members.forEach(m -> memberRepository.save(m));

        List<Member> findMembers = memberRepository.findUserCustomQuery("member2", 20);

        assertEquals(1, findMembers.size());
        assertEquals("member2", findMembers.get(0).getName());
        assertEquals(21, findMembers.get(0).getAge());
    }

    @Test
    public void testFindUsernameList() {
        List<Member> members = new ArrayList<>(Arrays.asList(
            new Member("member1", 10),
            new Member("member2", 13),
            new Member("member3", 21),
            new Member("member4", 16),
            new Member("member5", 25)
        ));
        members.forEach(m -> memberRepository.save(m));

        List<String> memberUsernames = memberRepository.findUsernameList();

        assertArrayEquals(
            new String[]{"member1", "member2", "member3", "member4", "member5"},
            memberUsernames.toArray()
        );
    }

    @Test
    public void testFindMemberDtoList() {
        Team team = new Team("team1");
        teamRepository.save(team);

        Member member = new Member("member1", 20, team);
        memberRepository.save(member);

        List<MemberDto> findMemberDtoList = memberRepository.findMemberDtoList();

        assertEquals(1, findMemberDtoList.size());
        assertEquals("member1", findMemberDtoList.get(0).getUsername());
        assertEquals(20, findMemberDtoList.get(0).getAge());
        assertEquals("team1", findMemberDtoList.get(0).getTeamName());
    }
}
