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

    // 값 조회
    @Query("select m.name from Member m")
    public List<String> findUsernameList();

    // DTO 조회
    @Query("select new study.jpadata.repository.MemberDto(m.id, m.name, m.age, t.name) from Member m join m.team t")
    public List<MemberDto> findMemberDtoList();

    @Query("select m from Member m where m.name in :names")
    public List<Member> findByNames(@Param("names") List<String> names);
}
