package study.jpadata.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.jpadata.entity.Member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
