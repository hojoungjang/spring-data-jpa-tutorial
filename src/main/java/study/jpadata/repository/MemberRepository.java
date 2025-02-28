package study.jpadata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import study.jpadata.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    public List<Member> findByNameAndAgeGreaterThan(String name, int age);

    @Query(name = "Member.findByUsername")
    public List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.name = :username and m.age > :age")
    public List<Member> findUserCustomQuery(
        @Param("username") String username,
        @Param("age") int age
    );
}
