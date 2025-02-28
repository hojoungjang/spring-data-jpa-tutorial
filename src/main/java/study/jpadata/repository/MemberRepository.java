package study.jpadata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import study.jpadata.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    public List<Member> findByNameAndAgeGreaterThan(String name, int age);
}
