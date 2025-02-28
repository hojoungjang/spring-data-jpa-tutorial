package study.jpadata.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import study.jpadata.entity.Member;

@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {
    
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        // Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        // Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        // Assertions.assertThat(findMember).isEqualTo(member);

        Assertions.assertEquals(findMember.getId(), member.getId());
        Assertions.assertEquals(findMember.getName(), member.getName());
        Assertions.assertSame(findMember, member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        
        //단건 조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        Assertions.assertSame(findMember1, member1);
        Assertions.assertSame(findMember2, member2);
        
        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        Assertions.assertEquals(all.size(), 2);
        
        //카운트 검증
        long count = memberJpaRepository.count();
        Assertions.assertEquals(count, 2);
        
        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deletedCount = memberJpaRepository.count();
        Assertions.assertEquals(0, deletedCount);
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
        members.forEach(m -> memberJpaRepository.save(m));

        List<Member> findMembers1 = memberJpaRepository.findByUsernameAndAgeGreaterThan("member1", 5);
        Assertions.assertEquals(findMembers1.size(), 2);
        findMembers1.forEach(m -> {
            Assertions.assertEquals(m.getName(), "member1");
            Assertions.assertTrue(m.getAge() > 5);
        });
    }
}
