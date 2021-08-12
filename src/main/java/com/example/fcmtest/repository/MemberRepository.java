package com.example.fcmtest.repository;

import com.example.fcmtest.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(Member member){
        em.persist(member);

        return member.getId();
    }

    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    @Transactional
    public Long remove(Member member) {
        em.remove(member);

        return member.getId();
    }
}
